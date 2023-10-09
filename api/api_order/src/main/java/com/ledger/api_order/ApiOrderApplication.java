package com.ledger.api_order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@MapperScan("com.ledger.api_order.mapper")
@ComponentScan({"com.ledger.api_order","com.ledger.api_common","com.ledger.api_user"})
@SpringBootApplication
public class ApiOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiOrderApplication.class, args);
    }

}
