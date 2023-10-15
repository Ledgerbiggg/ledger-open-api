package com.ledger.api_interface.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ledger.api_common.model.query.PageQuery;
import com.ledger.api_common.response.Result;
import com.ledger.api_interface.model.domain.InterfaceInfo;
import com.ledger.api_interface.model.dto.InterfaceInfo.InterfaceInfoAdminEditDetailRequest;
import com.ledger.api_interface.model.dto.InterfaceInfo.InterfaceInfoCallRequest;
import com.ledger.api_interface.model.dto.InterfaceInfo.InterfaceInfoListSearchRequest;
import com.ledger.api_interface.model.dto.InterfaceInfo.InterfaceInfoSDKCallRequest;
import com.ledger.api_interface.model.dto.RequestParameters.RequestParametersRequest;
import com.ledger.api_interface.model.dto.ResponseParameters.ResponseParametersRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoAdminQueryDetailRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoAdminQueryListRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoQueryListRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoWithParams;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 22866
* @description 针对表【interface_info(存储接口信息的表)】的数据库操作Service
* @createDate 2023-10-02 14:29:00
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    Result<Page<InterfaceInfoQueryListRequest>> getInterfaceList(PageQuery pageQuery,String search);

    Result<InterfaceInfoWithParams> getInterfaceById(String id);

    Result<Object> call(InterfaceInfoCallRequest interfaceInfoCallRequest);


    Result<List<InterfaceInfoAdminQueryListRequest>> adminGetInterfaceList(InterfaceInfoListSearchRequest interfaceInfoCallRequest);

    Result<InterfaceInfoAdminQueryDetailRequest> getInterfaceDetailById(String id);

    Result<Object> externalCall(InterfaceInfoSDKCallRequest interfaceInfoSDKCallRequest, HttpServletRequest request,String id);

    Result<String> modifyInterfaceRequestParameters(RequestParametersRequest requestParametersVo);

    Result<String> modifyInterfaceResponseParameters(ResponseParametersRequest responseParametersRequest);

    Result<String> modifyInterfaceParameters(InterfaceInfoAdminEditDetailRequest interfaceInfoAdminEditDetailRequest);

}
