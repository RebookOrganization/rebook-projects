package com.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "images")
public class NewsImageUrl implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "news_item_id")
//    @JsonIgnore
//    private NewsItem newsItem;
    private long newsItemId;

    private String imageUrl;
    private String imageType;
    private Long imageSize;
    @Lob
    private byte[] picByte;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

//    public NewsItem getNewsItem() { return newsItem; }
//
//    public void setNewsItem(NewsItem newsItem) { this.newsItem = newsItem; }

    public long getNewsItemId() {
        return newsItemId;
    }

    public void setNewsItemId(long newsItemId) {
        this.newsItemId = newsItemId;
    }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getImageType() { return imageType; }

    public void setImageType(String imageType) { this.imageType = imageType; }

    public Long getImageSize() { return imageSize; }

    public void setImageSize(Long imageSize) { this.imageSize = imageSize; }

    public byte[] getPicByte() { return picByte; }

    public void setPicByte(byte[] picByte) { this.picByte = picByte; }
}
