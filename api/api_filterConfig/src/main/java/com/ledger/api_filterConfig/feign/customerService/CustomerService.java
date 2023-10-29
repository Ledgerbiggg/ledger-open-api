package com.ledger.api_filterConfig.feign.customerService;


import com.ledger.api_filterConfig.interceptor.FeignClientInterceptor;
import com.ledger.api_filterConfig.model.domain.sessionInfo.SessionInfo;
import com.ledger.api_filterConfig.model.domain.userInfo.UserInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "api-gateway", configuration = FeignClientInterceptor.class)
@RequestMapping("/api/customerService/feign")
public interface CustomerService {
    @PostMapping("/addASession")
    @ApiOperation("增加回话信息")
    void addASession(@RequestBody SessionInfo sessionInfo);

}
