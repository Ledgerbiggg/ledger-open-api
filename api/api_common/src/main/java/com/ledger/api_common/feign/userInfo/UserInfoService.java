package com.ledger.api_common.feign.userInfo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ledger.api_common.model.domain.userInfo.UserInfo;
import feign.Headers;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "api-userInfo")
@RequestMapping("/api/feign/user")
public interface UserInfoService {
    @GetMapping("/getUserByUsername")
    @ApiOperation("根据用户名获取用户")
    UserInfo getUserByUsername(String username);

    @GetMapping("/updateById")
    @ApiOperation("更新用户余额")
    void updateById(UserInfo userInfo);


    @GetMapping("/getOne")
    @ApiOperation("获取一个用户")
    UserInfo getOne(LambdaQueryWrapper<UserInfo> wrapper);

    @GetMapping("/getAuthByUserId")
    @ApiOperation("根据用户id获取权限")
    List<String> getAuthByUserId(String userId);



}
