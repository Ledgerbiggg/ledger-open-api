package com.ledger.api_filterConfig.feign.userInfo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ledger.api_filterConfig.interceptor.FeignClientInterceptor;
import com.ledger.api_filterConfig.model.domain.userInfo.UserInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "api-gateway", configuration = FeignClientInterceptor.class)
@RequestMapping("/api/user/feign")
public interface UserInfoService {
    @GetMapping("/getUserByUsername")
    @ApiOperation("根据用户名获取用户")
    UserInfo getUserByUsername(@RequestParam String username);

    @GetMapping("/updateById")
    @ApiOperation("更新用户余额")
    void updateById(@RequestParam UserInfo userInfo);


    @GetMapping("/getOne")
    @ApiOperation("获取一个用户")
    UserInfo getOne(@RequestParam LambdaQueryWrapper<UserInfo> wrapper);

    @GetMapping("/getAuthByUserId")
    @ApiOperation("根据用户id获取权限")
    List<String> getAuthByUserId(@RequestParam String userId);

    @GetMapping("/getById")
    @ApiOperation("根据用户id获取用户")
    UserInfo getById(@RequestParam String userId);




}
