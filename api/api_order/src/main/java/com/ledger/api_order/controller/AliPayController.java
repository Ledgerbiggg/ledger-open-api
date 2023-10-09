package com.ledger.api_order.controller;

import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ledger.api_common.response.Result;
import com.ledger.api_order.config.AliPayConfig;
import com.ledger.api_order.model.domain.AliPay;
import com.ledger.api_order.model.domain.Orders;
import com.ledger.api_order.model.dto.OrdersCreateRequest;
import com.ledger.api_order.service.AliPayService;
import com.ledger.api_order.service.OrdersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/order")
public class AliPayController {

    @Resource
    private AliPayService aliPayService;





    @GetMapping("/pay") // &subject=xxx&traceNo=xxx&totalAmount=xxx
    public void pay(AliPay aliPay, HttpServletResponse httpResponse)  {
        aliPayService.pay(aliPay, httpResponse);
    }

    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request)  {
        return aliPayService.payNotify(request);
    }



}