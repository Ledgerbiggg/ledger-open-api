package com.ledger.api_interface.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName response_parameters
 */
@TableName(value ="response_parameters")
@Data
public class ResponseParameters implements Serializable {
    /**
     * 参数的唯一标识符
     */
    @TableId
    private String id;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数的数据类型
     */
    private String type;

    /**
     * api的id
     */
    private String api_id;

    /**
     * 参数的描述信息
     */
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}