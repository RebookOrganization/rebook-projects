package com.crawler.bean.Response;

import com.crawler.model.ShareNews;
import java.util.List;

public class ShareResponse {

  private List<ShareNews> listShareNews;
  private Integer shareAmount;

  public ShareResponse(List<ShareNews> listShareNews, Integer shareAmount) {
    this.listShareNews = listShareNews;
    this.shareAmount = shareAmount;
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
}
