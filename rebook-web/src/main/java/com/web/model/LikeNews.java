package com.web.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "like_news")
public class LikeNews implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "news_item_id")
    private Long newsItemId;

    @Column(name = "is_like")
    private boolean isLike;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getNewsItemId() { return newsItemId; }

    public void setNewsItemId(Long newsItemId) { this.newsItemId = newsItemId; }

    public boolean isLike() { return isLike; }

    public void setLike(boolean like) { isLike = like; }
}
