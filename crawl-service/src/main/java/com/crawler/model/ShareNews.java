package com.crawler.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ShareNews implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "news_item_id")
    private Long newItemId;

    private boolean isShare;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getNewItemId() { return newItemId; }

    public void setNewItemId(Long newItemId) { this.newItemId = newItemId; }

    public boolean isShare() { return isShare; }

    public void setShare(boolean share) { isShare = share; }
}
