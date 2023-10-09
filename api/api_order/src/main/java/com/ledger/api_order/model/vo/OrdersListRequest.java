package com.ledger.api_order.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrdersListRequest {

    /**
     * 订单ID
     */
    @TableId
    private String id;

    /**
     * 订单日期
     */
    private LocalDateTime order_date;


    private String order_date_str;

    private String trade_date_str;

    private LocalDateTime trade_date;

    /**
     * 订单状态
     */
    private Boolean order_status;

    private String status;
    private String tag;

    /**
     * 总金额
     */
    private BigDecimal total_amount;

    /**
     * 支付方式
     */
    private String method_name;

    /**
     * 主题
     */
    private String subject;


    private Integer count;


}
