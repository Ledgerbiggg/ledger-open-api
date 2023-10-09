package com.ledger.api_order.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledger.api_common.model.query.PageQuery;
import com.ledger.api_common.response.Result;
import com.ledger.api_order.model.dto.OrderSearchRequest;
import com.ledger.api_order.model.dto.OrdersCreateRequest;
import com.ledger.api_order.model.vo.OrdersListRequest;
import com.ledger.api_order.service.OrdersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrdersService ordersService;


    @PostMapping("/createOrder")
    public Result<String> createOrder(@RequestBody OrdersCreateRequest ordersCreateRequest) {
        return ordersService.createOrder(ordersCreateRequest);
    }

    @GetMapping("/myWallet")
    public Result<BigDecimal> myWallet() {
        return ordersService.myWallet();
    }

    @GetMapping("/checkOrderPayState")
    public Result<String> checkOrderPayState(String orderId) {
        return ordersService.checkOrderPayState(orderId);
    }

    @GetMapping("/getOrderList")
    public Result<List<OrdersListRequest>> getOrderList(OrderSearchRequest orderSearchRequest) {
        return ordersService.getOrderList(orderSearchRequest);
    }


}
