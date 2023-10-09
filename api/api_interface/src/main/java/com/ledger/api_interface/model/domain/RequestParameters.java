package com.ledger.api_interface.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName request_parameters
 */
@TableName(value ="request_parameters")
@Data
public class RequestParameters implements Serializable {
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
     * 参数是否必选，1表示是，0表示否
     */
    private Integer is_required;

    /**
     * 参数的数据类型
     */
    private String type;

    /**
     * 参数的描述信息
     */
    private String description;

    /**
     * api的id
     */
    private String api_id;

    private String default_value;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}