//package com.web.service;
//
//import com.web.model.EmailVerifyToken;
//import com.web.model.Role;
//import com.web.model.User;
//import com.web.repository.EmailVerifyRepository;
//import com.web.repository.RoleRepository;
//import com.web.repository.UserRepository;
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//
//@Service
//public class AuthService {
//  @Autowired
//  private UserRepository userRepository;
//
//  @Autowired
//  private RoleRepository roleRepository;
//
//  @Autowired
//  private PasswordEncoder passwordEncoder;
//
//  @Qualifier("emailVerifyRepository")
//  @Autowired
//  private EmailVerifyRepository emailVerifyRepository;
//
//  @Autowired
//  private EmailSenderService emailSenderService;
//
//  public String registerUser(Model model, User user,
//      BindingResult bindingResult) {
//    Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
//    if(existingUser.isPresent()) {
//      model.addAttribute("message", "Người dùng đã tồn tại.");
//      model.addAttribute(user);
//      bindingResult.rejectValue("email", null, "Người dùng đã tồn tại.");
//      return "signup";
//    }
//    else
//    {
//      Set<Role> roles = new HashSet<>();
//      roles.add(roleRepository.findByName("ROLE_USER"));
//      user.setRoles(roles);
//      user.setPassword(passwordEncoder.encode(user.getPassword()));
//      userRepository.save(user);
//
//      EmailVerifyToken confirmationToken = new EmailVerifyToken(user);
//
//      emailVerifyRepository.save(confirmationToken);
//
//      SimpleMailMessage mailMessage = new SimpleMailMessage();
//      mailMessage.setTo(user.getEmail());
//      mailMessage.setSubject("Complete Registration!");
//      mailMessage.setFrom("rebook.thanhle@gmail.com");
//      mailMessage.setText("To confirm your account, please click here : "
//          +"http://localhost:8082/confirm-account?token="+confirmationToken.getVerifyToken());
//
//      emailSenderService.sendEmail(mailMessage);
//
//      model.addAttribute("registerSuccess", "Vui lòng check mail để đăng nhập vào hệ thống.");
//      return "login";
//    }
//  }
//
//  public String confirmUserAccount(Model model, String confirmationToken)
//  {
//    EmailVerifyToken token = emailVerifyRepository.findByVerifyToken(confirmationToken);
//    if(token != null) {
//      Optional<User> user = userRepository.findByEmail(token.getUser().getEmail());
//      if (user.isPresent()) {
//        user.get().setEmailVerified(true);
//        userRepository.save(user.get());
//        model.addAttribute("verifySuccess", "Tài khoản đã được xác thực.");
//        return "index";
//      }
//
//      model.addAttribute("verifyFail", "Không tìm thấy người dùng.");
//      return "login";
//    }
//    else {
//      model.addAttribute("verifyFail", "Không tìm thấy token.");
//      return "login";
//    }
//  }
//
//}
