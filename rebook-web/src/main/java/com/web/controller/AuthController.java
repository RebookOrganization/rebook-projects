package com.web.controller;

import com.web.bean.Request.LoginRequest;
import com.web.bean.Request.SignUpRequest;
import com.web.bean.Response.ApiResponse;
import com.web.bean.Response.AuthResponse;
import com.web.dto.UserRegistrationDTO;
import com.web.exception.BadRequestException;
import com.web.model.AuthProvider;
import com.web.model.EmailVerifyToken;
import com.web.model.Role;
import com.web.model.User;
import com.web.repository.EmailVerifyRepository;
import com.web.repository.RoleRepository;
import com.web.repository.UserRepository;
import com.web.service.EmailSenderService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin
@Controller
@RequestMapping("/")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Qualifier("emailVerifyRepository")
    @Autowired
    private EmailVerifyRepository emailVerifyRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/")
    public String home(Model model) { return "redirect:/index"; }

    @GetMapping(value = "/index")
    public String index(Model model) { return "index"; }

    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("navActive", "active");
        return "login";
    }

    @GetMapping(value = "/access-denied")
    public String accessDenied() { return "403"; }

    @GetMapping(value = "/signup")
    public String signup(Model model) {
        model.addAttribute("navActive", "active");
        return "signup";
    }

    @ModelAttribute("userDto")
    public UserRegistrationDTO userRegistrationDTO() {
        return new UserRegistrationDTO();
    }

    @RequestMapping(value="/signup", method=RequestMethod.POST)
    public String registerUser(Model model, @ModelAttribute("userDto") @Valid UserRegistrationDTO userDto,
        BindingResult bindingResult, HttpServletRequest request) {

        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
        if(existingUser.isPresent()) {
            model.addAttribute("message", "Người dùng đã tồn tại.");
            model.addAttribute(userDto);
            bindingResult.rejectValue("email", null, "Người dùng đã tồn tại.");
            return "signup";
        }

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        User user = new User();
        user.setRoles(roles);
        user.setName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);

        EmailVerifyToken confirmationToken = new EmailVerifyToken(user);
        emailVerifyRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("rebook.thanhle@gmail.com");
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        mailMessage.setText("To confirm your account, please click here : "
            + baseUrl + "/confirm-account?token=" + confirmationToken.getVerifyToken());

        emailSenderService.sendEmail(mailMessage);

        model.addAttribute("registerSuccess", "Vui lòng check mail để đăng nhập vào hệ thống.");
        return "login";

    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(Model model, @RequestParam("token")String confirmationToken)
    {
        EmailVerifyToken token = emailVerifyRepository.findByVerifyToken(confirmationToken);
        if(token != null) {
            Optional<User> user = userRepository.findByEmail(token.getUser().getEmail());
            if (user.isPresent()) {
                user.get().setEmailVerified(true);
                userRepository.save(user.get());
                model.addAttribute("verifySuccess", "Tài khoản đã được xác thực.");
                return "index";
            }

            model.addAttribute("verifyFail", "Không tìm thấy người dùng.");
            return "login";
        }
        else {
            model.addAttribute("verifyFail", "Không tìm thấy token.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }


}
