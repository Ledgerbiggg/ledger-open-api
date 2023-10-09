package com.ledger.api_interface.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledger.api_common.model.query.PageQuery;
import com.ledger.api_common.response.Result;
import com.ledger.api_interface.model.domain.CallHistory;
import com.ledger.api_interface.model.dto.callHistory.CallHistoryQueryRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoQueryListRequest;
import com.ledger.api_interface.service.CallHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "接口调用历史管理") // 添加 API 标签
@RequestMapping("/interfaceInfo")
public class InterfaceCallHistoryController {

    @Resource
    private CallHistoryService callHistoryService;

    @GetMapping("/callHistory")
    @ApiOperation("获取接口信息查询请求") // 添加 API 操作说明
    public Result<Page<CallHistoryQueryRequest>> callHistory(PageQuery pageQuery,String interfaceId) {
        return callHistoryService.callHistory(pageQuery,interfaceId);
    }

}
