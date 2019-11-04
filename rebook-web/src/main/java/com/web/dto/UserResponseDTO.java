package com.web.dto;

import com.web.model.Role;
import java.util.Set;

public class UserResponseDTO {

  private Long userId;
  private String name;
  private String email;
  private String imageUrl;
  private String phone;
  private String birthday;
  private String gender;
  private Set<Role> roles;

  public UserResponseDTO() {
  }

  public UserResponseDTO(Long userId, String name, String email, String imageUrl,
      String phone, String birthday, String gender,
      Set<Role> roles) {
    this.userId = userId;
    this.name = name;
    this.email = email;
    this.imageUrl = imageUrl;
    this.phone = phone;
    this.birthday = birthday;
    this.gender = gender;
    this.roles = roles;
  }

  public Long getUserId() { return userId; }

  public void setUserId(Long userId) { this.userId = userId; }

  public String getName() { return name; }

  public void setName(String name) { this.name = name; }

  public String getEmail() { return email; }

  public void setEmail(String email) { this.email = email; }

  public String getImageUrl() { return imageUrl; }

  public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

  public String getPhone() { return phone; }

  public void setPhone(String phone) { this.phone = phone; }

  public String getBirthday() { return birthday; }

  public void setBirthday(String birthday) { this.birthday = birthday; }

  public String getGender() { return gender; }

  public void setGender(String gender) { this.gender = gender; }

  public Set<Role> getRoles() { return roles; }

  public void setRoles(Set<Role> roles) { this.roles = roles; }
}
