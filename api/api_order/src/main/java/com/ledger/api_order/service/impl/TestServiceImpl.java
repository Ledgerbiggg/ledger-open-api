package com.ledger.api_order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ledger.api_common.response.Result;
import com.ledger.api_order.model.Test;
import com.ledger.api_order.service.TestService;
import com.ledger.api_order.mapper.TestMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 22866
 * @description 针对表【test】的数据库操作Service实现
 * @createDate 2024-11-07 11:09:31
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test>
        implements TestService {

    @Override
    public Result<List<String>> test() {
        return Result.success(List.of("test"));
    }
}




