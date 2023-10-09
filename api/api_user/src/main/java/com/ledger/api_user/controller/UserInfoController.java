package com.ledger.api_user.controller;

import com.ledger.api_common.response.Result;
import com.ledger.api_user.model.domain.UserInfo;
import com.ledger.api_user.model.dto.UserInfoLogin;
import com.ledger.api_user.model.dto.UserInfoRegister;
import com.ledger.api_user.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(tags = "用户信息管理")
@RequestMapping("/user")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/login")
    @ApiOperation("登录用户")
    public Result<String> login(@RequestBody UserInfoLogin user, HttpServletResponse response){
        return userInfoService.login(user, response);
    }
    @PostMapping("/register")
    @ApiOperation("注册用户")
    public Result<String> register(@RequestBody UserInfoRegister user, HttpServletResponse response){
        return userInfoService.register(user, response);
    }

    @GetMapping("/getUser")
    @ApiOperation("查询用户")
    public Result<UserInfo> getUser(){
        return Result.success(new UserInfo());
    }

}
