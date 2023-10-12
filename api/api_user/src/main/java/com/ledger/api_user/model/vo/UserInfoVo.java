package com.ledger.api_user.model.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;


@Data
public class UserInfoVo  {

    private String id;

    private String username;

    private BigDecimal account;

    private String mail;

    private String invitation_code;

    private String avatar;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}