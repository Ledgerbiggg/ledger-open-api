package com.ledger.api_user.model.dto;

import lombok.Data;

@Data
public class UserInfoLogin {
    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;



}
