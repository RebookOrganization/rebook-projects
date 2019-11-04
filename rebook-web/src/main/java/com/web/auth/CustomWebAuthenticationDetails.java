package com.web.auth;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

  private String email;
  private String password;

  public CustomWebAuthenticationDetails(HttpServletRequest request) {
    super(request);
    this.email = request.getParameter("email");
    this.password = request.getParameter("password");
  }

  public String getEmail() { return email; }

  public void setEmail(String email) { this.email = email; }

  public String getPassword() { return password; }

  public void setPassword(String password) { this.password = password; }
}
