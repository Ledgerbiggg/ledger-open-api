package com.ledger.api_interface;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.ledger.api_interface.mapper")
@ComponentScan({"com.ledger.api_interface","com.ledger.api_common","com.ledger.api_filterConfig"})
@EnableFeignClients(basePackages = "com.ledger.api_filterConfig.feign")//方法2
public class ApiInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiInterfaceApplication.class, args);
    }


}



