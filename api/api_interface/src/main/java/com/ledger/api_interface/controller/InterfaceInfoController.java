package com.ledger.api_interface.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledger.api_common.model.query.PageQuery;
import com.ledger.api_common.response.Result;
import com.ledger.api_interface.model.dto.InterfaceInfo.InterfaceInfoCallRequest;
import com.ledger.api_interface.model.dto.InterfaceInfo.InterfaceInfoListSearchRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoAdminQueryDetailRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoAdminQueryListRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoQueryListRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoWithParams;
import com.ledger.api_interface.service.InterfaceInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "接口信息管理") // 添加 API 标签
@RequestMapping("/interfaceInfo")
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;


    @GetMapping("/getInterfaceList")
    @ApiOperation("获取接口信息查询请求") // 添加 API 操作说明
    public Result<Page<InterfaceInfoQueryListRequest>> getInterfaceInfoQueryRequest(PageQuery pageQuery, String search) {
        return interfaceInfoService.getInterfaceList(pageQuery, search);
    }

    @GetMapping("/getInterfaceById")
    @ApiOperation("获取接口信息查询请求") // 添加 API 操作说明
    public Result<InterfaceInfoWithParams> getInterfaceById(String id) {
        return interfaceInfoService.getInterfaceById(id);
    }


    @PostMapping("/call")
    @ApiOperation("调用接口方法") // 添加 API 操作说明
    public Result<Object> call(@RequestBody InterfaceInfoCallRequest interfaceInfoCallRequest) {
        return interfaceInfoService.call(interfaceInfoCallRequest);
    }

    @GetMapping("/admin/getInterfaceList")
    @PreAuthorize("hasAnyAuthority('admin')")
    @ApiOperation("管理员获取所有的接口信息") // 添加 API 操作说明
    public Result<List<InterfaceInfoAdminQueryListRequest>> adminGetInterfaceList(InterfaceInfoListSearchRequest interfaceInfoCallRequest) {
        return interfaceInfoService.adminGetInterfaceList(interfaceInfoCallRequest);
    }

    @GetMapping("/admin/getInterfaceDetailById")
    @PreAuthorize("hasAnyAuthority('admin')")
    @ApiOperation("管理员获取所有的接口详细信息") // 添加 API 操作说明
    public Result<InterfaceInfoAdminQueryDetailRequest> getInterfaceDetailById(String id) {
        return interfaceInfoService.getInterfaceDetailById(id);
    }








//    @PostMapping("/admin/saveInterfaceList")
//    @PreAuthorize("hasAnyAuthority('admin')")
//    @ApiOperation("管理员保存接口信息") // 添加 API 操作说明
//    public Result<List<InterfaceInfoAdminQueryListRequest>> adminSaveInterfaceList(InterfaceInfoListSearchRequest interfaceInfoCallRequest) {
//        return interfaceInfoService.adminGetInterfaceList(interfaceInfoCallRequest);
//    }


}
