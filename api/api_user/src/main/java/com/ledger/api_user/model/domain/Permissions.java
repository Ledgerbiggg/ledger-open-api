package com.ledger.api_user.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 存储权限信息
 * @TableName permissions
 */
@TableName(value ="permissions")
@Data
public class Permissions implements Serializable {
    /**
     * 
     */
    @TableId
    private String id;

    /**
     * 权限名称，应该是唯一的
     */
    private String name;

    /**
     * 权限描述
     */
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}