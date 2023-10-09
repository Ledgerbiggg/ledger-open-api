package com.ledger.api_order.controller;


import com.ledger.api_common.response.Result;
import com.ledger.api_order.model.vo.PriceListQueryRequest;
import com.ledger.api_order.service.PriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
public class PriceController {

    @Resource
    private PriceService priceService;

    @GetMapping("/priceList")
    public Result<List<PriceListQueryRequest>> priceList() {
        return priceService.listPriceAll();
    }


}
