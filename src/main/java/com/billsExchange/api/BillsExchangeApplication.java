package com.billsExchange.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication(scanBasePackages = "com.billsExchange.api")
@EnableSwagger2
public class BillsExchangeApplication {

	private static final Logger LOGGER=LoggerFactory.getLogger(BillsExchangeApplication.class);
	
	public static void main(String[] args) {
		LOGGER.info("Class:"+LOGGER.getName()+" is getting initiated...");
		SpringApplication.run(BillsExchangeApplication.class, args);
	}
}
