package com.ledger.api_order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ledger.api_common.Exception.KnowException;
import com.ledger.api_common.model.query.PageQuery;
import com.ledger.api_common.response.Result;
import com.ledger.api_common.util.PageUtil;
import com.ledger.api_order.model.domain.Orders;
import com.ledger.api_order.model.domain.Price;
import com.ledger.api_order.model.dto.OrderSearchRequest;
import com.ledger.api_order.model.dto.OrdersCreateRequest;
import com.ledger.api_order.model.vo.OrdersListRequest;
import com.ledger.api_order.service.OrdersService;
import com.ledger.api_order.mapper.OrdersMapper;
import com.ledger.api_order.service.PriceService;
import com.ledger.api_user.model.domain.UserInfo;
import com.ledger.api_user.service.UserInfoService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * @author 22866
 * @description 针对表【orders】的数据库操作Service实现
 * @createDate 2023-10-05 17:14:23
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
        implements OrdersService {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private PriceService priceService;

    @Resource
    private OrdersMapper ordersMapper;


    @Override
    public Result<String> createOrder(OrdersCreateRequest ordersCreateRequest) {
        Orders orders = new Orders();
        BeanUtil.copyProperties(ordersCreateRequest, orders);
        orders.setId(UUID.randomUUID().toString());
        orders.setOrder_status(false);
        orders.setOrder_date(LocalDateTime.now());
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        UserInfo userByUsername = userInfoService.getUserByUsername(username);
        orders.setUser_id(userByUsername.getId());
        String priceId = ordersCreateRequest.getPrice_id();
        if (priceId == null) {
            throw new RuntimeException("商品错误");
        }
        Price price = priceService.getById(priceId);
        orders.setTotal_amount(price.getPrice());
        save(orders);
        return Result.success("订单创建成功", orders.getId());
    }

    @Override
    public Result<String> checkOrderPayState(String orderId) {

        Orders orders = getById(orderId);
        if (orders == null) {
            throw new KnowException("订单不存在");
        }

        if (!orders.getOrder_status()) {
            return Result.fail("订单未支付");
        }

        return Result.success("订单已完成");
    }

    @Override
    public Result<BigDecimal> myWallet() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInfo userInfo = userInfoService.getUserByUsername(username);
        return Result.success(userInfo.getAccount());
    }

    @Override
    public Result<List<OrdersListRequest>> getOrderList(OrderSearchRequest orderSearchRequest) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserInfo userByUsername = userInfoService.getUserByUsername(userDetails.getUsername());



        List<OrdersListRequest> list = ordersMapper.getOrderList(userByUsername.getId(),orderSearchRequest);

        list.forEach(item -> {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd \n HH:mm:ss");
            LocalDateTime orderDate = item.getOrder_date();
            LocalDateTime tradeDate = item.getTrade_date();
            item.setOrder_date_str(orderDate==null?null:orderDate.format(dateTimeFormatter));
            item.setTrade_date_str(tradeDate==null?null:tradeDate.format(dateTimeFormatter));
            item.setStatus(!item.getOrder_status() ?"未支付":"已支付");
            item.setTag(item.getMethod_name());
            item.setOrder_status(null);
            item.setTrade_date(null);
            item.setOrder_date(null);
        });

        return Result.success(list);
    }
}




