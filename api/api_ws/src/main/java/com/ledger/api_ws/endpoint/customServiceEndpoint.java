package com.ledger.api_ws.endpoint;


import com.alibaba.fastjson.JSON;
import com.ledger.api_common.util.JwtUtil;
import com.ledger.api_ws.domain.Message;
import com.ledger.api_ws.enums.FromNameEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//GetHttpSessionConfig是用了存储会话的
@ServerEndpoint(value = "/ws/{token}")
@Component
@Slf4j
// 每次创建一个连接就会new出来一个EndPoint
public class customServiceEndpoint {

    private static final Map<String, Session> SESSION_MAP = new ConcurrentHashMap<>();

    private static Session kk = null;


    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        // 将用户存入到map
        try {
            String secret = "ledger";
            if (JwtUtil.validateJwt(token, secret)) {
                // 如果用户是对的就将用户存入到map中
                String username = JwtUtil.getUserNameFromToken(token, secret);
                if ("kk".equals(username)) {
                    kk = session;
                } else {
                    SESSION_MAP.put(username, session);
                }
                Message message = new Message(kk != null, FromNameEnum.SYSTEM.getName(), username, "连接成功");
                sendMessage(message, session);
            } else {
                log.info("token校验失败");
                session.getBasicRemote().sendText("token校验失败");
                session.close();
            }
        } catch (IOException e) {
            log.error("token校验失败");
        }


    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("token") String token) {

    }

    @OnClose
    public void onClose(Session session) {
        String username="";
        for (Map.Entry<String, Session> stringSessionEntry : SESSION_MAP.entrySet()) {
            if (stringSessionEntry.getValue().equals(session)) {
                SESSION_MAP.remove(stringSessionEntry.getKey());
                username = stringSessionEntry.getKey();
                break;
            }
        }
        if(username.equals("kk")){
            // 客服下线
            kk = null;
        }
    }


    @OnError
    public void onError(Throwable error) {
        System.out.println("onError......" + error.getMessage());

    }


    /**
     * 单独发送一个消息
     *
     * @param message 消息
     * @param session 会话
     */
    private void sendMessage(Message message, Session session) {
        try {
            log.info("服务端向客户端{}发送消息{}", session.getId(), message);
            session.getBasicRemote().sendText(JSON.toJSONString(message));
        } catch (IOException e) {
            log.error("消息发送失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 将数据发送给所有的客户端
     *
     * @param message 消息内容
     */
    private void sendAllMessage(String message) {
        SESSION_MAP.forEach((k, v) -> {
            log.info("服务端向客户端{}发送消息{}", v.getId(), message);
            try {
                v.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("客户端发送消息失败");
                throw new RuntimeException(e);
            }
        });
    }


}
