package com.ledger.api_ws.endpoint;


import com.ledger.api_ws.config.GetHttpSessionConfig;
import com.ledger.api_ws.config.WebsocketConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

//GetHttpSessionConfig是用了存储会话的
@ServerEndpoint(value = "/testWs", configurator = GetHttpSessionConfig.class)
// 每次创建一个连接就会new出来一个EndPoint
public class TestEndpoint {


    @OnOpen
    public void onOpen(Session session,EndpointConfig config) {
        try {
            session.getBasicRemote().sendText("成功建立起连接");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @OnMessage
    public void onMessage(Session session){

    }
    @OnClose
    public void onClose(Session session){

    }


    @OnError
    public void onError(Throwable error) {
        System.out.println("onError......"+error.getMessage());


    }





}
