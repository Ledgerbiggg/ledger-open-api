package com.ledger.api_user.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ledger.api_common.response.Result;
import com.ledger.api_filterConfig.model.domain.userInfo.UserInfo;
import com.ledger.api_user.model.dto.UserInfoLogin;
import com.ledger.api_user.model.dto.UserInfoRegister;
import com.ledger.api_user.model.vo.UploadVo;
import com.ledger.api_user.model.vo.UserInfoVo;
import com.ledger.api_user.service.UserInfoService;
import com.ledger.api_user.service.UserPermissionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Api(tags = "用户信息管理")
@RequestMapping("/user")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserPermissionsService userPermissionsService;

    @PostMapping("/login")
    @ApiOperation("登录用户")
    public Result<String> login(@RequestBody UserInfoLogin user, HttpServletResponse response) {
        return userInfoService.login(user, response);
    }

    @PostMapping("/register")
    @ApiOperation("注册用户")
    public Result<String> register(@RequestBody UserInfoRegister user, HttpServletResponse response) {
        return userInfoService.register(user, response);
    }

    @GetMapping("/getUser")
    @ApiOperation("查询用户")
    public Result<UserInfoVo> getUser() {
        return userInfoService.getUser();
    }


    @PostMapping("/uploadAvatar")
    @ApiOperation("上传头像")
    public Result<UploadVo> uploadAvatar(@RequestBody MultipartFile file) {
        return userInfoService.uploadAvatar(file);
    }


    @GetMapping("/getUserIcon")
    @ApiOperation("获取头像")
    public Result<String> getUserIcon() {
        return userInfoService.getUserIcon();
    }


//    @GetMapping("/getAvatarCheck")
//    @ApiOperation("校验身份")
//    public Result<String> getAvatarCheck() {
//        return userInfoService.getAvatarCheck();
//    }
//
//
//    @GetMapping("/getAvatar")
//    @ApiOperation("获取头像")
//    public void getAvatar(String fileName,String token,HttpServletResponse response) {
//        userInfoService.getAvatar(fileName,token,response);
//    }





    @GetMapping("/feign/getUserByUsername")
    @ApiOperation("根据用户名获取用户")
    public UserInfo getUserByUsername(String username) {
        return userInfoService.getUserByUsername(username);
    }

    @GetMapping("/feign/updateById")
    @ApiOperation("更新用户余额")
    public void updateById(UserInfo userInfo) {
        userInfoService.updateById(userInfo);
    }

    @GetMapping("/feign/getOne")
    @ApiOperation("获取一个用户")
    public UserInfo getOne(LambdaQueryWrapper<UserInfo> wrapper) {
        return userInfoService.getOne(wrapper);
    }

    @GetMapping("/feign/getAuthByUserId")
    @ApiOperation("根据用户id获取权限")
    public List<String> getAuthByUserId(String userId) {
        return userPermissionsService.getAuthByUserId(userId);
    }


    @GetMapping("/feign/getById")
    @ApiOperation("根据用户id获取用户")
    public UserInfo getById(String userId) {
        return userInfoService.getById(userId);
    }


}
