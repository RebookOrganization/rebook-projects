package com.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class MainCrawler {

	private static final Logger logger =
			LoggerFactory.getLogger(MainCrawler.class);

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MainCrawler.class, args);
	}

}
