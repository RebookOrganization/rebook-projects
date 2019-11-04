package com.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

  @Autowired
  private final KafkaTemplate<String, Object> template;

  @Value("advice-topic")
  private final String topicName;

  public ProducerService(KafkaTemplate<String, Object> template,
    @Value("advice-topic") final String topicName) {
    this.template = template;
    this.topicName = topicName;
  }

  public void pushStringMessage(String message){
    template.send(topicName, message);
  }

}
