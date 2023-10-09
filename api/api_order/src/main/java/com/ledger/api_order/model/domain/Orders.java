package com.ledger.api_order.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName orders
 */
@TableName(value ="orders")
@Data
public class Orders implements Serializable {
    /**
     * 订单ID
     */
    @TableId
    private String id;

    /**
     * 用户ID
     */
    private String user_id;

    /**
     * 订单日期
     */
    private LocalDateTime order_date;


    private LocalDateTime trade_date;

    /**
     * 订单状态
     */
    private Boolean order_status;

    /**
     * 总金额
     */
    private BigDecimal total_amount;

    /**
     * 支付方式
     */
    private String payment_method;

    /**
     * 主题
     */
    private String subject;


    /**
     * 价格id
     */
    private String price_id;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}