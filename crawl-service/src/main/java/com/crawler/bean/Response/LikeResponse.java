package com.crawler.bean.Response;

import com.crawler.model.LikeNews;
import java.util.List;

public class LikeResponse {

  private List<LikeNews> listLike;
  private Integer likeAmount;

  public LikeResponse(List<LikeNews> listLike, Integer likeAmount) {
    this.listLike = listLike;
    this.likeAmount = likeAmount;
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
}
