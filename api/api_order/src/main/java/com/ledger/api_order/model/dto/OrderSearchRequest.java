package com.ledger.api_order.model.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderSearchRequest {

    private String subject;
    private String traceNo;
    private BigDecimal totalAmount;
    private Boolean orderState;




}
