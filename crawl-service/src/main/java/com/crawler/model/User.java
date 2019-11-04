package com.crawler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "user", uniqueConstraints =
    {@UniqueConstraint(columnNames = "email")})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Email
  @Column(nullable = false)
  private String email;

  private boolean emailVerified;

  @JsonIgnore
  private String password;

  @Enumerated(EnumType.STRING)
  private AuthProvider provider;

  @Column(name = "provider_id")
  private String providerId;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "birth_date")
  private String birthDate;

  @Column(length = 25)
  private String gender;

  @ManyToMany
  @JsonIgnore
  private Set<Role> roles;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<NewsItem> newsItems;

  @Temporal(TemporalType.TIMESTAMP)
  private Date lastLogin;

  private String ipLogin;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public AuthProvider getProvider() {
    return provider;
  }

  public void setProvider(AuthProvider provider) {
    this.provider = provider;
  }

  public String getProviderId() {
    return providerId;
  }

  public void setProviderId(String providerId) {
    this.providerId = providerId;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Set<NewsItem> getNewsItems() {
    return newsItems;
  }

  public void setNewsItems(Set<NewsItem> newsItems) {
    this.newsItems = newsItems;
  }

  public Date getLastLogin() { return lastLogin; }

  public void setLastLogin(Date lastLogin) { this.lastLogin = lastLogin; }

  public String getIpLogin() { return ipLogin; }

  public void setIpLogin(String ipLogin) { this.ipLogin = ipLogin; }

  public boolean isEmailVerified() { return emailVerified; }

  public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }
}
