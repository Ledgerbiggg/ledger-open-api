package com.ledger.api_order.service;

import com.ledger.api_order.model.domain.AliPay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AliPayService {

    void pay(AliPay aliPay, HttpServletResponse httpResponse) ;

    String payNotify(HttpServletRequest request);
}
