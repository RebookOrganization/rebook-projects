package com.crawler.bean.Request;

import com.crawler.bean.Response.UploadFileResponse;
import java.util.List;

public class PostNewsRequest {

  private String title;
  List<UploadFileResponse> listUpload;
  private String price;
  private String area;
  private String desc;
  private String direct_house;
  private String room_number;
  private String floor_number;
  private String toilet_number;
  private String interior;
  private String pub_date;

  private String prop_address;

  private String ownerName;
  private String ownerPhone;
  private String ownerAddress;

  private String project_name;
  private String project_owner;
  private String project_size;

  private Long user_id;

  public String getTitle() { return title; }

  public void setTitle(String title) { this.title = title; }

  public List<UploadFileResponse> getListUpload() { return listUpload; }

  public void setListUpload(List<UploadFileResponse> listUpload) {
    this.listUpload = listUpload;
  }

  public String getPrice() { return price; }

  public void setPrice(String price) { this.price = price; }

  public String getArea() { return area; }

  public void setArea(String area) { this.area = area; }

  public String getDesc() { return desc; }

  public void setDesc(String desc) { this.desc = desc; }

  public String getDirect_house() { return direct_house; }

  public void setDirect_house(String direct_house) { this.direct_house = direct_house; }

  public String getRoom_number() { return room_number; }

  public void setRoom_number(String room_number) { this.room_number = room_number; }

  public String getFloor_number() { return floor_number; }

  public void setFloor_number(String floor_number) { this.floor_number = floor_number; }

  public String getToilet_number() { return toilet_number; }

  public void setToilet_number(String toilet_number) { this.toilet_number = toilet_number; }

  public String getInterior() { return interior; }

  public void setInterior(String interior) { this.interior = interior; }

  public String getPub_date() { return pub_date; }

  public void setPub_date(String pub_date) { this.pub_date = pub_date; }

  public Long getUser_id() { return user_id; }

  public void setUser_id(Long user_id) { this.user_id = user_id; }

  public String getProp_address() { return prop_address; }

  public void setProp_address(String prop_address) { this.prop_address = prop_address; }

  public String getOwnerName() { return ownerName; }

  public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

  public String getOwnerPhone() { return ownerPhone; }

  public void setOwnerPhone(String ownerPhone) { this.ownerPhone = ownerPhone; }

  public String getOwnerAddress() { return ownerAddress; }

  public void setOwnerAddress(String ownerAddress) { this.ownerAddress = ownerAddress; }

  public String getProject_name() { return project_name; }

  public void setProject_name(String project_name) { this.project_name = project_name; }

  public String getProject_owner() { return project_owner; }

  public void setProject_owner(String project_owner) { this.project_owner = project_owner; }

  public String getProject_size() { return project_size; }

  public void setProject_size(String project_size) { this.project_size = project_size; }
}
