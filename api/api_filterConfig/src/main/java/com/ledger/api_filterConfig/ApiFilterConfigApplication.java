package com.ledger.api_filterConfig;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan({"com.ledger.api_common","com.ledger.api_filterConfig"})
@EnableFeignClients(basePackages = "com.ledger.api_filterConfig.feign")//方法2
public class ApiFilterConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiFilterConfigApplication.class, args);
    }
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        //发送请求的模板
        return new RestTemplate();
    }
}
