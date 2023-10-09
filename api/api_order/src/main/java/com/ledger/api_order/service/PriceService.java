package com.ledger.api_order.service;

import com.ledger.api_common.response.Result;
import com.ledger.api_order.model.domain.Price;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ledger.api_order.model.vo.PriceListQueryRequest;

import java.util.List;

/**
* @author 22866
* @description 针对表【price】的数据库操作Service
* @createDate 2023-10-05 15:28:35
*/
public interface PriceService extends IService<Price> {

    Result<List<PriceListQueryRequest>> listPriceAll();


}
