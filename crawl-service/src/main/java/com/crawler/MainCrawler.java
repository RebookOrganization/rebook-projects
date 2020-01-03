package com.crawler;

import com.crawler.utils.ConvertData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * this module which consists of rest services can also run without web module.
 */
@Configuration
@SpringBootApplication
@ComponentScan("com.crawler.*")
public class MainCrawler implements ApplicationRunner {

	private static final Logger logger =
			LoggerFactory.getLogger(MainCrawler.class);

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MainCrawler.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("2.2 ty: {}",ConvertData.convertPriceNum("2.2 tỷ",1f));
		logger.info("'65 triệu/m²: {}",ConvertData.convertPriceNum("65 triệu/m²",230));
		logger.info("300 triệu: {}",ConvertData.convertPriceNum("300 triệu",1f));
		logger.info("1 tỷ 200 triệu: {}",ConvertData.convertPriceNum("1 tỷ 200 triệu",1f));
	}
}
