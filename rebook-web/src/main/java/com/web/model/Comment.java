package com.web.model;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "comment")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "news_item_id")
    private Long newItemId;

    private String content;
    private Date timeComment;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getNewItemId() { return newItemId; }

    public void setNewItemId(Long newItemId) { this.newItemId = newItemId; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public Date getTimeComment() { return timeComment; }

    public void setTimeComment(Date timeComment) { this.timeComment = timeComment; }
}
