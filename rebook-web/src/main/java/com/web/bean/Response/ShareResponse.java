package com.web.bean.Response;

import com.web.model.ShareNews;
import java.util.List;

public class ShareResponse {

  private List<ShareNews> listShareNews;
  private Integer shareAmount;
  private boolean userIsShare;

  public ShareResponse(List<ShareNews> listShareNews, Integer shareAmount) {
    this.listShareNews = listShareNews;
    this.shareAmount = shareAmount;
  }

  public ShareResponse(List<ShareNews> listShareNews, Integer shareAmount, boolean userIsShare) {
    this.listShareNews = listShareNews;
    this.shareAmount = shareAmount;
    this.userIsShare = userIsShare;
  }

  public List<ShareNews> getListShareNews() {
    return listShareNews;
  }

  public void setListShareNews(List<ShareNews> listShareNews) {
    this.listShareNews = listShareNews;
  }

  public Integer getShareAmount() {
    return shareAmount;
  }

  public void setShareAmount(Integer shareAmount) {
    this.shareAmount = shareAmount;
  }

  public boolean isUserIsShare() { return userIsShare; }

  public void setUserIsShare(boolean userIsShare) { this.userIsShare = userIsShare; }

}
