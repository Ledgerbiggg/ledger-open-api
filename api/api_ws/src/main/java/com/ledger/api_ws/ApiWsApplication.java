package com.ledger.api_ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.ledger.api_ws","com.ledger.api_common"})
@EnableFeignClients(basePackages = "com.ledger.api_filterConfig.feign")//方法2
public class ApiWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiWsApplication.class, args);
	}
}
