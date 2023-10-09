package com.ledger.api_interface.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ledger.api_common.model.query.PageQuery;
import com.ledger.api_common.response.Result;
import com.ledger.api_interface.model.domain.InterfaceInfo;
import com.ledger.api_interface.model.dto.InterfaceInfo.InterfaceInfoCallRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoQueryListRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoWithParams;

import java.util.HashMap;

/**
* @author 22866
* @description 针对表【interface_info(存储接口信息的表)】的数据库操作Service
* @createDate 2023-10-02 14:29:00
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    Result<Page<InterfaceInfoQueryListRequest>> getInterfaceList(PageQuery pageQuery,String search);

    Result<InterfaceInfoWithParams> getInterfaceById(String id);

    Result<Object> call(InterfaceInfoCallRequest interfaceInfoCallRequest);

}
