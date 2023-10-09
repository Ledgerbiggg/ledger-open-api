package com.ledger.api_order.mapper;

import com.ledger.api_order.model.domain.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ledger.api_order.model.dto.OrderSearchRequest;
import com.ledger.api_order.model.vo.OrdersListRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 22866
* @description 针对表【orders】的数据库操作Mapper
* @createDate 2023-10-05 17:14:23
* @Entity com.ledger.api_order.model.domain.Orders
*/
public interface OrdersMapper extends BaseMapper<Orders> {

    List<OrdersListRequest> getOrderList(
            @Param("id") String id,
            @Param("orderSearchRequest") OrderSearchRequest orderSearchRequest);
}




