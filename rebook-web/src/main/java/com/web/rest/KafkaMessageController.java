package com.web.rest;

import com.web.config.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaMessageController {
  private static final Logger logger =
      LoggerFactory.getLogger(KafkaMessageController.class);

  @Autowired
  private ProducerService producerService;

  @GetMapping("/message")
  public String message(@RequestParam(name="value") String message){
    producerService.pushStringMessage(message);
    return "OK";
  }

}
