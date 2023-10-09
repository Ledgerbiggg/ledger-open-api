package com.ledger.api_order.model.domain;

import lombok.Data;

@Data
public class AliPay {
    //主题
    private String subject;
    //跟踪号
    private String traceNo;
    //总金额
    private double totalAmount;

    private String alipayTraceNo;
}