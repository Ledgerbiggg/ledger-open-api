package com.ledger.api_customerService.controller;

import com.ledger.api_common.response.Result;
import com.ledger.api_filterConfig.model.domain.sessionInfo.SessionInfo;
import com.ledger.api_customerService.model.dto.SessionInfoDTO;
import com.ledger.api_customerService.model.vo.SessionInfoVo;
import com.ledger.api_customerService.service.SessionInfoService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags = "客服服务") // 添加 API 标签
@RequestMapping("/customerService")
public class CustomerServiceController {
    @Resource
    private SessionInfoService sessionInfoService;

    @GetMapping("/getAllSession")
    public Result<SessionInfoVo> getAllSession() {
        return sessionInfoService.getAllSession();
    }

    @GetMapping("/getMySession")
    public Result<List<SessionInfoDTO>> getMySession(HttpServletRequest request) {
        return sessionInfoService.getMySession(request);
    }

    @PostMapping("/feign/addASession")
    public void addASession(@RequestBody SessionInfo sessionInfo) {
        sessionInfoService.save(sessionInfo);
    }


}
