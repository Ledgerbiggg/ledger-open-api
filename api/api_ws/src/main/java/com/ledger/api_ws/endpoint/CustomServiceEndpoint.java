package com.ledger.api_ws.endpoint;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ledger.api_common.util.JwtUtil;
import com.ledger.api_common.util.LocalDateTimeUtil;
import com.ledger.api_filterConfig.feign.customerService.CustomerService;
import com.ledger.api_filterConfig.model.domain.sessionInfo.SessionInfo;
import com.ledger.api_ws.domain.Message;
import com.ledger.api_ws.enums.FromNameEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

//GetHttpSessionConfig是用了存储会话的
@ServerEndpoint(value = "/ws/{token}")
@Component
@Slf4j
// 每次创建一个连接就会new出来一个EndPoint
public class CustomServiceEndpoint {

    @Resource
    private CustomerService customerService;

    private static final Map<String, Session> SESSION_MAP = new ConcurrentHashMap<>();

    private static Session kk = null;


    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        // 将用户存入到map
        try {
            String username = validateTokenAndGetUsername(token);
            if (StrUtil.isNotBlank(username)) {
                if ("kk".equals(username)) {
                    kk = session;
                    Message message = new Message(kk != null,
                            FromNameEnum.SYSTEM.getName(),
                            "客服上线",
                            username,
                            LocalDateTimeUtil.getNowTimeFormat());
                    sendAllMessage(message);
                    return;
                } else {
                    SESSION_MAP.put(username, session);
                }
                Message message = new Message(kk != null,
                        FromNameEnum.SYSTEM.getName(),
                        "连接成功",
                        username,
                        LocalDateTimeUtil.getNowTimeFormat());
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
        String username = validateTokenAndGetUsername(token);
        if(Objects.equals(username, "kk")){
            // 客服发送的消息 ,带有发送者
            Message msg = JSON.parseObject(message, Message.class);
            msg.setKk(true);
            msg.setFromName(username);
            msg.setFormatDt(LocalDateTimeUtil.getNowTimeFormat());
            sendMessage(msg, SESSION_MAP.get(msg.getToName()));
            SessionInfo sessionInfo = new SessionInfo();
            sessionInfo.setUser_id(msg.getToName());
            sessionInfo.setMessage(msg.getMessage());
            sessionInfo.setUsername(username);
            sessionInfo.setDt(LocalDateTime.now());
            customerService.addASession(sessionInfo);
        }else {
            //用户发送给客服
            Message msg = JSON.parseObject(message, Message.class);
            if (kk != null) {
                msg.setKk(true);
                msg.setFromName(username);
                msg.setFormatDt(LocalDateTimeUtil.getNowTimeFormat());
                sendMessage(msg, kk);
            }else {
                //客服未上线
                sendMessage(new Message(false, FromNameEnum.SYSTEM.getName(), "客服未上线", username,LocalDateTimeUtil.getNowTimeFormat()), session);
            }
            SessionInfo sessionInfo = new SessionInfo();
            sessionInfo.setUser_id(username);
            sessionInfo.setMessage(msg.getMessage());
            sessionInfo.setUsername(username);
            sessionInfo.setDt(LocalDateTime.now());
            customerService.addASession(sessionInfo);
        }
    }

    @OnClose
    public void onClose(Session session) {
        String username = "";
        for (Map.Entry<String, Session> stringSessionEntry : SESSION_MAP.entrySet()) {
            if (stringSessionEntry.getValue().equals(session)) {
                SESSION_MAP.remove(stringSessionEntry.getKey());
                username = stringSessionEntry.getKey();
                break;
            }
        }
        if (session.equals(kk)) {
            // 客服下线
            kk = null;
            Message message = new Message(false, FromNameEnum.SYSTEM.getName(), "客服已下线", username, LocalDateTimeUtil.getNowTimeFormat());
            sendAllMessage(message);
        }
    }


    @OnError
    public void onError(Throwable error) {
        System.out.println("onError......" + error.getMessage());
    }

    /**
     * 单独发送一个消息
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
    private void sendAllMessage(Message message) {
        SESSION_MAP.forEach((k, v) -> {
            log.info("服务端向客户端{}发送消息{}", v.getId(), message);
            try {
                v.getBasicRemote().sendText(JSON.toJSONString(message));
            } catch (IOException e) {
                log.error("客户端发送消息失败");
                throw new RuntimeException(e);
            }
        });
    }

    private String validateTokenAndGetUsername(String token) {
        if (JwtUtil.validateJwt(token, "ledger")) {
            return JwtUtil.getUserNameFromToken(token, "ledger");
        } else {
            return null;
        }
    }


}
