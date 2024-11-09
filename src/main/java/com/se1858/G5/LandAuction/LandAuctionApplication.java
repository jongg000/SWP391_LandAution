package com.se1858.G5.LandAuction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LandAuctionApplication {
	public static void main(String[] args) {
		SpringApplication.run(LandAuctionApplication.class, args);
	}
}
