package com.ledger.api_order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@MapperScan("com.ledger.api_order.mapper")
@ComponentScan({"com.ledger.api_order","com.ledger.api_common","com.ledger.api_filterConfig"})
@SpringBootApplication
@EnableFeignClients(basePackages = "com.ledger.api_filterConfig.feign")//方法2
public class ApiOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiOrderApplication.class, args);
    }

}
