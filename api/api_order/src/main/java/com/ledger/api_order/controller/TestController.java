package com.ledger.api_order.controller;

import com.ledger.api_common.response.Result;
import com.ledger.api_order.model.vo.PriceListQueryRequest;
import com.ledger.api_order.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping("/test")
    public Result<List<String>> test() {
        return testService.test();
    }
}
