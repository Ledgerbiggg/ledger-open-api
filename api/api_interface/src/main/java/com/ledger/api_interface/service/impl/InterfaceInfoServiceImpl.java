package com.ledger.api_interface.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ledger.api_common.Exception.KnowException;
import com.ledger.api_common.model.query.PageQuery;
import com.ledger.api_common.response.Result;
import com.ledger.api_common.util.PageUtil;
import com.ledger.api_common.util.ResUtils;
import com.ledger.api_interface.mapper.InterfaceInfoMapper;
import com.ledger.api_interface.model.domain.CallHistory;
import com.ledger.api_interface.model.domain.InterfaceInfo;
import com.ledger.api_interface.model.domain.RequestParameters;
import com.ledger.api_interface.model.domain.ResponseParameters;
import com.ledger.api_interface.model.dto.InterfaceInfo.InterfaceInfoCallRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoQueryListRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoWithParams;
import com.ledger.api_interface.service.CallHistoryService;
import com.ledger.api_interface.service.InterfaceInfoService;
import com.ledger.api_interface.service.RequestParametersService;
import com.ledger.api_interface.service.ResponseParametersService;
import com.ledger.api_user.model.domain.UserInfo;
import com.ledger.api_user.service.UserInfoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @author 22866
 * @description 针对表【interface_info(存储接口信息的表)】的数据库操作Service实现
 * @createDate 2023-10-02 14:29:00
 */
@Service
@Transactional
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;
    @Resource
    private RequestParametersService requestParametersService;

    @Resource
    private ResponseParametersService responseParametersService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private CallHistoryService callHistoryService;


    @Override
    public Result<Page<InterfaceInfoQueryListRequest>> getInterfaceList(PageQuery pageQuery, String search) {
        LambdaQueryWrapper<InterfaceInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(search), InterfaceInfo::getName, search);
        wrapper.like(StrUtil.isNotBlank(search), InterfaceInfo::getDescription, search);
        Page<InterfaceInfo> interfaceInfoPage =
                interfaceInfoMapper.selectPage(
                        PageUtil.getPageFromPageQuery(pageQuery, InterfaceInfo.class), wrapper);
        Page<InterfaceInfoQueryListRequest> page =
                PageUtil.getRecordsBySource(interfaceInfoPage, InterfaceInfoQueryListRequest.class, count(wrapper));

        return Result.success(page);
    }

    @Override
    public Result<InterfaceInfoWithParams> getInterfaceById(String id) {
        LambdaQueryWrapper<InterfaceInfo> wrapper =
                new LambdaQueryWrapper<>();
        wrapper.eq(InterfaceInfo::getId, id);
        InterfaceInfo interfaceInfo = getOne(wrapper);
        LambdaQueryWrapper<RequestParameters> requestParametersLambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        requestParametersLambdaQueryWrapper.eq(RequestParameters::getApi_id, id);
        List<RequestParameters> requestParametersList =
                requestParametersService.list(requestParametersLambdaQueryWrapper);
        LambdaQueryWrapper<ResponseParameters> responseParametersLambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        responseParametersLambdaQueryWrapper.eq(ResponseParameters::getApi_id, id);
        List<ResponseParameters> responseParametersList =
                responseParametersService.list(responseParametersLambdaQueryWrapper);
        InterfaceInfoWithParams interfaceInfoWithParams = new InterfaceInfoWithParams();
        interfaceInfoWithParams.setRequestParametersList(requestParametersList);
        interfaceInfoWithParams.setResponseParametersList(responseParametersList);
        BeanUtil.copyProperties(interfaceInfo, interfaceInfoWithParams);
        return Result.success(interfaceInfoWithParams);
    }

    @Override
    public Result<Object> call(InterfaceInfoCallRequest interfaceInfoCallRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        UserInfo userByUsername = userInfoService.getUserByUsername(username);
        BigDecimal divide = userByUsername.getAccount().subtract(new BigDecimal("1"));
        if(divide.compareTo(new BigDecimal(0))<0){
            throw new KnowException("余额不足");
        }
        userByUsername.setAccount(divide);
        interfaceInfoMapper.addCount(interfaceInfoCallRequest.getInterfaceId());
        userInfoService.updateById(userByUsername);

        Object jsonObject;
        String method = interfaceInfoCallRequest.getMethod();
        String url = interfaceInfoCallRequest.getUrl();
        HashMap<String, Object> params = interfaceInfoCallRequest.getParams();
        if ("POST".equalsIgnoreCase(method)) {
            jsonObject = ResUtils.postDataForCommon(url, params);
        } else if ("GET".equalsIgnoreCase(method)) {
            jsonObject = ResUtils.getDataForCommon(url, params);
        } else {
            throw new KnowException("不正确的调用方法");
        }
        String id = userByUsername.getId();
        CallHistory callHistory = new CallHistory();
        callHistory.setUser_id(id);
        callHistory.setCall_time(LocalDateTime.now());
        callHistory.setInsterface_id(interfaceInfoCallRequest.getInterfaceId());
        if(jsonObject instanceof JSONArray){
            JSONArray jsonArray = (JSONArray) jsonObject;
            callHistory.setResult(jsonArray.toJSONString());
        } else if (jsonObject instanceof JSONObject) {
            JSONObject jsonObject1 = (JSONObject) jsonObject;
            callHistory.setResult(jsonObject1.toJSONString());
        }
        callHistoryService.save(callHistory);


        return Result.success(jsonObject);
    }
}




