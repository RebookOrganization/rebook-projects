package com.web.rest;

import com.web.service.UserService;
import com.web.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
public class UserNewsController {

  @Autowired
  private UserService userService;

  @GetMapping(value = "/news-item")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<?> getAllNewsUser(@RequestParam Long userID) throws Exception {
    return new ResponseEntity<>(userService.getAllNewsByUser(userID), HttpStatus.OK);
  }

}
