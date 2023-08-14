package com.unsw.shopful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ShopfulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopfulApplication.class, args);
	}

}
