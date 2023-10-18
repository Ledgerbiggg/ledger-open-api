package com.ledger.api_user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.ledger.api_user.mapper")
@ComponentScan({"com.ledger.api_user","com.ledger.api_common"})
@EnableFeignClients(basePackages = "com.ledger.api_common.feign")//方法2
public class ApiUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiUserApplication.class, args);
    }


    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        //发送请求的模板
        return new RestTemplate();
    }

}
