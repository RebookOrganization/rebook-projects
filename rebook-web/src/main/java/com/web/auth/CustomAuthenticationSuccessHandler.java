package com.web.auth;

import com.web.repository.UserRepository;
import com.web.utils.DateTimeUtils;
import com.web.utils.IpUtils;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private static final Logger logger = LoggerFactory
      .getLogger(CustomAuthenticationSuccessHandler.class);

  public static final String HOME_REBOOK = "/home";

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Autowired
  UserRepository userRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {

    logger.info("Custom redirect on authentication success");
    String referer = httpServletRequest.getHeader("referer");
    try {

      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if (Objects.nonNull(auth) && Objects.nonNull(auth.getDetails())) {
        CustomWebAuthenticationDetails user = (CustomWebAuthenticationDetails) auth.getDetails();

        if (StringUtils.isNoneBlank(user.getEmail())) {
          userRepository.updateLogin(DateTimeUtils.getCurDateWithMilisec(),
              IpUtils.getClientIp(httpServletRequest), user.getEmail());
        }
      }
    } catch (Exception e) {
      logger.info(e.getMessage(), e);
    }

//    handleRedirect(referer, httpServletRequest, httpServletResponse);
  }

//  private void handleRedirect(String referer, HttpServletRequest request,
//      HttpServletResponse response) throws IOException {
//    if (StringUtils.isNotEmpty(referer)) {
//      redirectStrategy.sendRedirect(request, response, "/index");
//    }
//  }

}
