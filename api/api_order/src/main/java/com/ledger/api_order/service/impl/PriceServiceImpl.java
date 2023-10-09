package com.ledger.api_order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ledger.api_common.response.Result;
import com.ledger.api_order.model.domain.Price;
import com.ledger.api_order.model.vo.PriceListQueryRequest;
import com.ledger.api_order.service.PriceService;
import com.ledger.api_order.mapper.PriceMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 22866
* @description 针对表【price】的数据库操作Service实现
* @createDate 2023-10-05 15:28:35
*/
@Service
public class PriceServiceImpl extends ServiceImpl<PriceMapper, Price>
    implements PriceService{

    @Override
    public Result<List<PriceListQueryRequest>> listPriceAll() {
        LambdaQueryWrapper<Price> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Price::getPrice);
        List<Price> list = list(wrapper);
        List<PriceListQueryRequest> collect =
                list.stream()
                        .map(price -> BeanUtil.copyProperties(price, PriceListQueryRequest.class))
                        .collect(Collectors.toList());

        return Result.success(collect);
    }
}




