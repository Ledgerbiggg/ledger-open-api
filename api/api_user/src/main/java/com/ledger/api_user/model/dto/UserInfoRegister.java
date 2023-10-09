package com.ledger.api_user.model.dto;

import lombok.Data;

@Data
public class UserInfoRegister {

    private String username;
    private String password;
    private String password2;

}
