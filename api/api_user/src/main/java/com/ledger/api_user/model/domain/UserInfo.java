package com.ledger.api_user.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 存储用户信息
 * @TableName user_info
 */
@TableName(value ="user_info")
@Data
public class UserInfo implements Serializable {
    /**
     * 
     */
    @TableId
    private String id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐户是否启用
     */
    private Integer enabled;

    /**
     * 帐户是否未过期
     */
    private Integer account_non_expired;

    /**
     * 帐户是否未锁定
     */
    private Integer account_non_locked;

    /**
     * 凭证是否未过期
     */
    private Integer credentials_non_expired;

    /**
     * 用户权限，逗号分隔的权限列表
     */
    private String authorities;

    private BigDecimal account;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}