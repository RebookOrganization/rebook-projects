package com.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * since basePackage includes com.apiDemo.* and api module is imported, api components will also be invoked.
 */
@Configuration
@ComponentScan("com.web.*")
@SpringBootApplication
@EnableJpaRepositories
@EnableKafka
@EnableScheduling
@EnableCaching
public class WebMain {

	private static final Logger logger =
			LoggerFactory.getLogger(WebMain.class);

	public static boolean IS_DEV_ENV = true;

	private static ApplicationContext ctx;

	public static void main(String[] args) throws Exception {
		ctx = SpringApplication.run(WebMain.class, args);
	}

	public static ApplicationContext getCtx() {
		return ctx;
	}

}
