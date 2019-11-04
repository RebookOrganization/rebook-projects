package com.crawler.model;

import static javax.persistence.GenerationType.IDENTITY;

import com.crawler.dto.ChatMessageDTO.MessageType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ChatMessage implements java.io.Serializable{
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(length = 60)
  private MessageType messageType;

  @Column(name = "content", length = 250)
  private String content;

  @Column(name = "sender", length = 45)
  private String sender;

  public Long getId() { return id; }

  public void setId(Long id) { this.id = id; }

  public MessageType getMessageType() { return messageType; }

  public void setMessageType(MessageType messageType) { this.messageType = messageType; }

  public String getContent() { return content; }

  public void setContent(String content) { this.content = content; }

  public String getSender() { return sender; }

  public void setSender(String sender) { this.sender = sender; }
}
