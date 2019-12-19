package com.web.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

  private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

  @Autowired
  private ProducerService producerService;

  @Scheduled(fixedRate = 540000)
  public void scheduleTaskWithFixedRate() {
    logger.info("Fixed Rate Batdongsan.com.vn :: Execution Time - {}",
        dateTimeFormatter.format(LocalDateTime.now()));
    producerService.pushStringMessage("BATDONGSAN");

  }

  @Scheduled(fixedRate = 540000)
  public void scheduleCrawlerDiaOcOnline() {
    logger.info("Fixed Rate DiaOcOnline.vn :: Execution Time - {}",
        dateTimeFormatter.format(LocalDateTime.now()));
    producerService.pushStringMessage("DIAOCONLINE");
  }

}
