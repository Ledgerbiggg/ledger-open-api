package com.ledger.api_ws.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Map;

public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // 获取httpsSession对象
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        // 用来存储会话的自定义属性
        Map<String, Object> userProperties = sec.getUserProperties();
        // 存储session
        userProperties.put(HttpSession.class.getName(), httpSession);
    }



}
