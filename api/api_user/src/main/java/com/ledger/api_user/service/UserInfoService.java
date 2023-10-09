package com.ledger.api_user.service;

import com.ledger.api_common.response.Result;
import com.ledger.api_user.model.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ledger.api_user.model.dto.UserInfoLogin;
import com.ledger.api_user.model.dto.UserInfoRegister;

import javax.servlet.http.HttpServletResponse;

/**
* @author 22866
* @description 针对表【user_info(存储用户信息)】的数据库操作Service
* @createDate 2023-09-30 17:32:12
*/
public interface UserInfoService extends IService<UserInfo> {
    Result<String> login(UserInfoLogin user, HttpServletResponse response);

    UserInfo getUserByUsername(String username);

    Result<String> register(UserInfoRegister user, HttpServletResponse response);
}
