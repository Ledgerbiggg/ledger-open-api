package com.ledger.api_order.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName price
 */
@TableName(value ="price")
@Data
public class Price implements Serializable {
    /**
     * id
     */
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

    /**
     * 上线状态
     */
    private Integer state;

    /**
     * 更新时间
     */
    private Date update_time;

    /**
     * 创建时间
     */
    private Date create_time;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}