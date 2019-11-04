package com.web.controller;

import com.web.dto.PasswordResetDto;
import com.web.model.PasswordResetToken;
import com.web.model.User;
import com.web.repository.PasswordResetTokenRepository;
import com.web.repository.UserRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reset-password")
public class PasswordResetController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordResetTokenRepository passwordResetTokenRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @ModelAttribute("passwordResetForm")
  public PasswordResetDto passwordReset() {
    return new PasswordResetDto();
  }

  @GetMapping
  public String displayResetPasswordPage(@RequestParam(required = false) String token,
      Model model) {

    PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
    if (resetToken == null){
      model.addAttribute("error", "Could not find password reset token.");
    } else if (resetToken.isExpired()){
      model.addAttribute("error", "Token has expired, please request a new password reset.");
    } else {
      model.addAttribute("token", resetToken.getToken());
    }

    return "reset-password";
  }

  @PostMapping
  @Transactional
  public String handlePasswordReset(@ModelAttribute("passwordResetForm") @Valid PasswordResetDto form,
      BindingResult result,
      RedirectAttributes redirectAttributes) {

    if (result.hasErrors()){
      redirectAttributes.addFlashAttribute(BindingResult.class.getName() + ".passwordResetForm", result);
      redirectAttributes.addFlashAttribute("passwordResetForm", form);
      return "redirect:/reset-password?token=" + form.getToken();
    }

    PasswordResetToken token = passwordResetTokenRepository.findByToken(form.getToken());
    User user = token.getUser();
    String updatedPassword = passwordEncoder.encode(form.getPassword());
    userRepository.updatePassword(updatedPassword, user.getId());
    passwordResetTokenRepository.delete(token);

    return "redirect:/login?resetSuccess";
  }
}
