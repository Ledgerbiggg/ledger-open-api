package com.ledger.api_ws.controller;

import com.ledger.api_filterConfig.feign.customerService.CustomerService;
import com.ledger.api_filterConfig.model.domain.sessionInfo.SessionInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ws")
public class TestController {

    @Resource
    private CustomerService customerService;

    @GetMapping("/test")
    public String test() {
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setId("456156156156412");
        sessionInfo.setUsername("test");
        sessionInfo.setUser_id("test");
        sessionInfo.setUser_icon("test");
        sessionInfo.setMessage("test");

        customerService.addASession(sessionInfo);
        return "test";
    }

}
