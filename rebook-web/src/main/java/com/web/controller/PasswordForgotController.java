package com.web.controller;

import com.web.dto.PasswordForgotDto;
import com.web.model.MailTemplate;
import com.web.model.PasswordResetToken;
import com.web.model.User;
import com.web.repository.PasswordResetTokenRepository;
import com.web.repository.UserRepository;
import com.web.service.EmailSenderService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forgot-password")
public class PasswordForgotController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordResetTokenRepository passwordResetTokenRepository;

  @Autowired
  private EmailSenderService emailSenderService;

  @ModelAttribute("forgotPasswordForm")
  public PasswordForgotDto forgotPasswordDto() {
    return new PasswordForgotDto();
  }

  @GetMapping
  public String displayForgotPasswordPage() {
    return "forgot-password";
  }

  @PostMapping
  public String processForgotPasswordForm(@ModelAttribute("forgotPasswordForm") @Valid PasswordForgotDto form,
      BindingResult result, HttpServletRequest request) {

    if (result.hasErrors()){
      return "forgot-password";
    }

    Optional<User> user = userRepository.findByEmail(form.getEmail());
    if (!user.isPresent()){
      result.rejectValue("email", null, "We could not find an account for that e-mail address.");
      return "forgot-password";
    }

    PasswordResetToken token = new PasswordResetToken();
    token.setToken(UUID.randomUUID().toString());
    token.setUser(user.get());
    token.setExpirationDate(30);
    passwordResetTokenRepository.save(token);

    MailTemplate mail = new MailTemplate();
    mail.setFrom("rebook.thanhle@gmail.com");
    mail.setTo(user.get().getEmail());
    mail.setSubject("Password reset request");

    Map<String, Object> model = new HashMap<>();
    model.put("token", token);
    model.put("user", user.get());
    model.put("signature", "https://rebook.com.vn");
    String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    model.put("resetUrl", url + "/reset-password?token=" + token.getToken());
    mail.setModel(model);
    emailSenderService.sendMailTemplate(mail);

    return "redirect:/forgot-password?success";

  }
}
