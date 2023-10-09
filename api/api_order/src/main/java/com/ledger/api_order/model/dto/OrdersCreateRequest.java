package com.ledger.api_order.model.dto;

import lombok.Data;

@Data
public class OrdersCreateRequest {


    /**
     * 主题
     */
    private String subject;

    /**
     * 价格id
     */
    private String price_id;

}
