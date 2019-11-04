package com.web.service;

import com.web.model.MailTemplate;
import java.nio.charset.StandardCharsets;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service("emailSenderService")
public class EmailSenderService {

  @Autowired
  private JavaMailSender javaMailSender;

  @Autowired
  private SpringTemplateEngine templateEngine;

  @Autowired
  public EmailSenderService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Async
  public void sendEmail(SimpleMailMessage email) {
    javaMailSender.send(email);
  }

  public void sendMailTemplate(MailTemplate mailTemplate) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message,
          MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
          StandardCharsets.UTF_8.name());

      Context context = new Context();
      context.setVariables(mailTemplate.getModel());
      String html = templateEngine.process("email-template", context);

      helper.setTo(mailTemplate.getTo());
      helper.setText(html, true);
      helper.setSubject(mailTemplate.getSubject());
      helper.setFrom(mailTemplate.getFrom());

      javaMailSender.send(message);
    } catch (Exception e){
      throw new RuntimeException(e);
    }
  }
}
