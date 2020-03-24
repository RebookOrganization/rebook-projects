package com.web.controller;

import com.web.auth.CurrentUser;
import com.web.auth.UserPrincipal;
import com.web.bean.Response.UserResponse;
import com.web.dto.UserResponseDTO;
import com.web.exception.ResourceNotFoundException;
import com.web.model.User;
import com.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class HomeController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/me")
  public UserResponseDTO getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
    UserResponseDTO userResponseDTO = new UserResponseDTO();
    User user = userRepository.findById(userPrincipal.getId())
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

    userResponseDTO.setUserId(user.getId());
    userResponseDTO.setName(user.getName());
    userResponseDTO.setEmail(user.getEmail());
    userResponseDTO.setImageUrl(user.getImageUrl());
    userResponseDTO.setPhone(user.getPhoneNumber());
    userResponseDTO.setBirthday(user.getBirthDate());
    userResponseDTO.setGender(user.getGender());
    userResponseDTO.setRoles(user.getRoles());
    userResponseDTO.setBackgroundImage(user.getBackgroundImage());

    return userResponseDTO;
  }

  @GetMapping("/me-no-role")
  public UserResponse getUserWithoutRole(@CurrentUser UserPrincipal userPrincipal) {
    UserResponse userResponse = new UserResponse();
    User user = userRepository.findById(userPrincipal.getId())
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

    userResponse.setUserId(user.getId());
    userResponse.setName(user.getName());
    userResponse.setBirthDate(user.getBirthDate());
    userResponse.setImageUrl(user.getImageUrl());
    userResponse.setEmail(user.getEmail());
    userResponse.setPhone(user.getPhoneNumber());
    userResponse.setGender(user.getGender());

    return userResponse;
  }

  @GetMapping("/principal")
  public UserPrincipal getCurrentPrincipal(@CurrentUser UserPrincipal userPrincipal) {
    return userPrincipal;
  }
}
