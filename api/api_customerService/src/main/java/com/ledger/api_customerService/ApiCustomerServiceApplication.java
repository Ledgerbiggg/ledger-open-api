package com.ledger.api_customerService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@MapperScan("com.ledger.api_customerService.mapper")
@ComponentScan({"com.ledger.api_customerService","com.ledger.api_common","com.ledger.api_filterConfig"})
@EnableFeignClients(basePackages = "com.ledger.api_filterConfig.feign")//方法2
@SpringBootApplication
public class ApiCustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCustomerServiceApplication.class, args);
	}

}
