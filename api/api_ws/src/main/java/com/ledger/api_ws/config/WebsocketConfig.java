package com.ledger.api_ws.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

//@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
@Configuration
public class WebsocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        // 创建一个用用于启用websocket支持的类,将这个注册成为一个bean就可以启用websocket
        return new ServerEndpointExporter();
    }
}
