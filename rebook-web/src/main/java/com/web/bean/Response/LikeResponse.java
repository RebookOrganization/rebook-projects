package com.web.bean.Response;

import com.web.model.LikeNews;
import java.util.List;

public class LikeResponse {

  private List<LikeNews> listLike;
  private Integer likeAmount;
  private boolean userIsLike;

  public LikeResponse(List<LikeNews> listLike, Integer likeAmount) {
    this.listLike = listLike;
    this.likeAmount = likeAmount;
  }

  public LikeResponse(List<LikeNews> listLike, Integer likeAmount, boolean userIsLike) {
    this.listLike = listLike;
    this.likeAmount = likeAmount;
    this.userIsLike = userIsLike;
  }

  public List<LikeNews> getListLike() {
    return listLike;
  }

  public void setListLike(List<LikeNews> listLike) {
    this.listLike = listLike;
  }

  public Integer getLikeAmount() {
    return likeAmount;
  }

  public void setLikeAmount(Integer likeAmount) {
    this.likeAmount = likeAmount;
  }

  public boolean isUserIsLike() { return userIsLike; }

  public void setUserIsLike(boolean userIsLike) { this.userIsLike = userIsLike; }
}
