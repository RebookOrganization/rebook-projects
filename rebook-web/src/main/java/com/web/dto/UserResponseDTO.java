package com.web.dto;

import com.web.model.Role;
import com.web.model.User;
import java.util.Set;
import javax.jws.soap.SOAPBinding.Use;

public class UserResponseDTO {

  private Long userId;
  private String name;
  private String email;
  private String imageUrl;
  private String phone;
  private String birthday;
  private String gender;
  private Set<Role> roles;
  private String backgroundImage;

  public UserResponseDTO() { }

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

  public String getBackgroundImage() {
    return backgroundImage;
  }

  public void setBackgroundImage(String backgroundImage) {
    this.backgroundImage = backgroundImage;
  }

  public void mappingFromEntity(User user) {
    this.userId = user.getId();
    this.name = user.getName();
    this.imageUrl = user.getImageUrl();
    this.email = user.getEmail();
    this.birthday = user.getBirthDate();
    this.gender = user.getGender();
    this.phone = user.getPhoneNumber();
    this.roles = user.getRoles();
    this.backgroundImage = user.getBackgroundImage();
  }

}
