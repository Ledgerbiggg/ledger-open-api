package com.ledger.api_ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.ledger.api_ws","com.ledger.api_common"})
public class ApiWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiWsApplication.class, args);
	}

}
