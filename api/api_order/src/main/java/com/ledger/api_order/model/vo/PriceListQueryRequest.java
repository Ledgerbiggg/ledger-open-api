package com.ledger.api_order.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceListQueryRequest {

    @TableId
    private String id;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 货币数量
     */
    private Integer count;


}
