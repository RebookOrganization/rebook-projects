package com.web.controller;

import com.web.model.ChatMessage;
import com.web.dto.ChatMessageDTO;
import com.web.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

  @Autowired
  ChatMessageRepository chatMessageRepository;

  @MessageMapping("/chat.sendMessage")
  @SendTo("/topic/public")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ChatMessageDTO sendMessage(@Payload ChatMessageDTO chatMessageDTO) {
    ChatMessage chatMessage = new ChatMessage();
    chatMessage.setMessageType(chatMessageDTO.getType());
    chatMessage.setContent(chatMessageDTO.getContent());
    chatMessage.setSender(chatMessageDTO.getSender());
    chatMessageRepository.save(chatMessage);
    return chatMessageDTO;
  }

  @MessageMapping("/chat.addUser")
  @SendTo("/topic/public")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ChatMessageDTO addUser(@Payload ChatMessageDTO dto, SimpMessageHeaderAccessor headerAccessor) {
    // Add current username in web socket session
    headerAccessor.getSessionAttributes().put("username", dto.getSender());
    ChatMessage chatMessage = new ChatMessage();
    chatMessage.setMessageType(dto.getType());
    chatMessage.setSender(dto.getSender());
    chatMessageRepository.save(chatMessage);
    return dto;
  }
}
