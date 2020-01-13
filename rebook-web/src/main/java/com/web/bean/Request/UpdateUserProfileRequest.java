package com.web.bean.Request;

public class UpdateUserProfileRequest {
  private long userId;
  private String name;
  private String imageUrl;
  private String phoneNumber;
  private String birthDate;
  private String gender;

  public UpdateUserProfileRequest() {
  }

  public long getUserId() { return userId; }

  public void setUserId(long userId) { this.userId = userId; }

  public String getName() { return name; }

  public void setName(String name) { this.name = name; }

  public String getImageUrl() { return imageUrl; }

  public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

  public String getPhoneNumber() { return phoneNumber; }

  public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

  public String getBirthDate() { return birthDate; }

  public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

  public String getGender() { return gender; }

  public void setGender(String gender) { this.gender = gender; }
}
