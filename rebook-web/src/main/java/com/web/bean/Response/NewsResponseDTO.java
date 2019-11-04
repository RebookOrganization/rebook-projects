package com.web.bean.Response;

import com.web.model.Comment;
import com.web.model.LikeNews;
import com.web.model.NewsImageUrl;
import com.web.model.ShareNews;
import java.util.List;
import java.util.Set;

public class NewsResponseDTO {

  private Long newsId;
  private String titleNews;
  private String summaryNews;
  private String descriptionNews;
  private String pubDate;
  private String price;
  private String area;
  private String address_prop;

  private Long userId;
  private String imageUser;
  private String username;

  private String contactName;
  private String contactPhone;
  private String contactEmail;

  private String projectName;
  private String projectOwner;
  private String projectSize;

  private List<LikeNews> likeNewsList;
  private List<Comment> commentList;
  private List<ShareNews> shareNewsList;

  private Set<NewsImageUrl> imageUrlList;

  public String getTitleNews() { return titleNews; }

  public void setTitleNews(String titleNews) { this.titleNews = titleNews; }

  public String getSummaryNews() { return summaryNews; }

  public void setSummaryNews(String summaryNews) { this.summaryNews = summaryNews; }

  public String getDescriptionNews() { return descriptionNews; }

  public void setDescriptionNews(String descriptionNews) { this.descriptionNews = descriptionNews; }

  public String getPubDate() { return pubDate; }

  public void setPubDate(String pubDate) { this.pubDate = pubDate; }

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

  public String getAddress_prop() {
    return address_prop;
  }

  public void setAddress_prop(String address_prop) {
    this.address_prop = address_prop;
  }

  public String getImageUser() { return imageUser; }

  public void setImageUser(String imageUser) { this.imageUser = imageUser; }

  public String getUsername() { return username; }

  public void setUsername(String username) { this.username = username; }

  public Set<NewsImageUrl> getImageUrlList() {
    return imageUrlList;
  }

  public void setImageUrlList(Set<NewsImageUrl> imageUrlList) {
    this.imageUrlList = imageUrlList;
  }

  public Long getNewsId() {
    return newsId;
  }

  public void setNewsId(Long newsId) {
    this.newsId = newsId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getProjectOwner() {
    return projectOwner;
  }

  public void setProjectOwner(String projectOwner) {
    this.projectOwner = projectOwner;
  }

  public String getProjectSize() {
    return projectSize;
  }

  public void setProjectSize(String projectSize) {
    this.projectSize = projectSize;
  }

  public List<LikeNews> getLikeNewsList() {
    return likeNewsList;
  }

  public void setLikeNewsList(List<LikeNews> likeNewsList) {
    this.likeNewsList = likeNewsList;
  }

  public List<Comment> getCommentList() {
    return commentList;
  }

  public void setCommentList(List<Comment> commentList) {
    this.commentList = commentList;
  }

  public List<ShareNews> getShareNewsList() {
    return shareNewsList;
  }

  public void setShareNewsList(List<ShareNews> shareNewsList) {
    this.shareNewsList = shareNewsList;
  }
}
