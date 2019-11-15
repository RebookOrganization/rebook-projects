package com.crawler.service;

import com.crawler.bean.Response.CommonResponse;
import java.io.IOException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

  private static final Logger logger =
      LoggerFactory.getLogger(ConsumerService.class);

  @Autowired
  private CrawlerService crawlerService;

  @KafkaListener(topics = "${kafka.topic}", clientIdPrefix = "json",
      groupId = "${kafka.consumer.group-id}",
      containerFactory = "kafkaListenerContainerFactory")
  public void listenAsObject(ConsumerRecord<String, Object> consumerRecord,
      @Payload String payload) throws IOException {
    logger.info("Message: {}", payload);
    String mess = payload.substring(1, payload.length()-1);
    if (mess.equals("BATDONGSAN")) {
      CommonResponse response = crawlerService.crawlerBatDongSan();
      int returnCode = response.getReturnCode();
      if (returnCode == 1) {
        logger.info("Crawler BatDongSan.com.vn response - {}", response.getResult());
      }
      else {
        logger.error("Crawler BatDongSan.com.vn Exception - {}", response.getReturnMessage());
      }
    }
    else if (mess.equals("DIAOCONLINE")) {
      CommonResponse response = crawlerService.crawlerDiaOcOnline();
      int returnCode = response.getReturnCode();
      if (returnCode == 1) {
        logger.info("Crawler DiaOcOnline.vn response - {}", response.getResult());
      }
      else {
        logger.error("Crawler DiaOcOnline.vn Exception - {}", response.getReturnMessage());
      }
    }
    else {
      logger.info("kafkaListenerContainerFactory message mistake.");
    }
  }
}
