package com.unsw.shopful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableCaching
@EnableMongoAuditing
public class ShopfulApplication {

	public static final Logger log = LoggerFactory.getLogger(ShopfulApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ShopfulApplication.class, args);
	}

}
