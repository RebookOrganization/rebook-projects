package com.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserSearchLog implements java.io.Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long userId;
  private String searchDate;
  private Long millisecond;
  private String content;
  private String price;
  private String area;
  private String trans_type;
  private String description;
  private String city;
  private String room_number;
  private String floor_number;
  private String toilet_number;
  private String balcony;
  private String wardin;
  private String interior;
  private String address;
  private String project;

  public Long getId() { return id; }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getSearchDate() { return searchDate; }

  public void setSearchDate(String searchDate) { this.searchDate = searchDate; }

  public Long getMillisecond() { return millisecond; }

  public void setMillisecond(Long millisecond) { this.millisecond = millisecond; }

  public String getContent() { return content; }

  public void setContent(String content) { this.content = content; }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getTrans_type() {
    return trans_type;
  }

  public void setTrans_type(String trans_type) {
    this.trans_type = trans_type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getRoom_number() {
    return room_number;
  }

  public void setRoom_number(String room_number) {
    this.room_number = room_number;
  }

  public String getFloor_number() {
    return floor_number;
  }

  public void setFloor_number(String floor_number) {
    this.floor_number = floor_number;
  }

  public String getToilet_number() {
    return toilet_number;
  }

  public void setToilet_number(String toilet_number) {
    this.toilet_number = toilet_number;
  }

  public String getBalcony() {
    return balcony;
  }

  public void setBalcony(String balcony) {
    this.balcony = balcony;
  }

  public String getWardin() {
    return wardin;
  }

  public void setWardin(String wardin) {
    this.wardin = wardin;
  }

  public String getInterior() {
    return interior;
  }

  public void setInterior(String interior) {
    this.interior = interior;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getProject() {
    return project;
  }

  public void setProject(String project) {
    this.project = project;
  }
}
