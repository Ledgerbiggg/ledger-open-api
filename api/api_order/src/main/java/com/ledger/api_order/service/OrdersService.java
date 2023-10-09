package com.ledger.api_order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledger.api_common.model.query.PageQuery;
import com.ledger.api_common.response.Result;
import com.ledger.api_order.model.domain.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ledger.api_order.model.dto.OrderSearchRequest;
import com.ledger.api_order.model.dto.OrdersCreateRequest;
import com.ledger.api_order.model.vo.OrdersListRequest;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 22866
* @description 针对表【orders】的数据库操作Service
* @createDate 2023-10-05 17:14:23
*/
public interface OrdersService extends IService<Orders> {

    Result<String> createOrder(OrdersCreateRequest ordersCreateRequest);

    Result<String> checkOrderPayState(String orderId);

    Result<BigDecimal> myWallet();

    Result<List<OrdersListRequest>> getOrderList(OrderSearchRequest orderSearchRequest);
}
