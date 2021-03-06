package com.web.rest;

import com.web.bean.Request.UpdateBackgroundRequest;
import com.web.bean.Request.UpdateUserProfileRequest;
import com.web.service.UserService;
import com.web.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping(value = "/update-profile")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<?> updateProfileUser(@RequestBody UpdateUserProfileRequest request) throws Exception {
    return new ResponseEntity<>(userService.updateUserProfile(request), HttpStatus.OK);
  }

  @PostMapping("/update-background")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<?> updateBackgroundImage(@RequestBody UpdateBackgroundRequest request) {
    return new ResponseEntity<>(userService.updateBackgroundImage(request), HttpStatus.OK);
  }
}
