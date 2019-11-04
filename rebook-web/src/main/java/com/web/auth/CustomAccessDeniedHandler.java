package com.web.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.bean.Response.ErrorResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    try {
      ObjectMapper mapper = new ObjectMapper();
      ErrorResponse res = new ErrorResponse();
      res.setMessage("I am sorry. " +
          "I know who you are, I believe who you say you are but you just do not have permission to access this resource. " +
          "Maybe if you ask the system administrator nicely, you will get permission. But please don not bother me again until your predicament changes.");
      res.setCode(403);
      mapper.writeValue(response.getOutputStream(), res);
    } catch (Exception ex) {
      throw new ServletException();
    }
  }
}
