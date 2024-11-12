package com.ledger.api_order.service;

import com.ledger.api_common.response.Result;
import com.ledger.api_order.model.Test;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ledger.api_order.model.vo.PriceListQueryRequest;

import java.util.List;

/**
 * @author 22866
 * @description 针对表【test】的数据库操作Service
 * @createDate 2024-11-07 11:09:31
 */
public interface TestService extends IService<Test> {
    Result<List<String>> test();
}
