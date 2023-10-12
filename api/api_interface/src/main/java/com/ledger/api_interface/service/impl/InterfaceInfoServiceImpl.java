package com.ledger.api_interface.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;

import java.io.*;
import java.util.Base64;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ledger.api_common.Exception.KnowException;
import com.ledger.api_common.model.query.PageQuery;
import com.ledger.api_common.response.Result;
import com.ledger.api_common.util.FileUtil;
import com.ledger.api_common.util.HttpUtil;
import com.ledger.api_common.util.PageUtil;
import com.ledger.api_common.util.ResUtils;
import com.ledger.api_interface.mapper.InterfaceInfoMapper;
import com.ledger.api_interface.model.domain.CallHistory;
import com.ledger.api_interface.model.domain.InterfaceInfo;
import com.ledger.api_interface.model.domain.RequestParameters;
import com.ledger.api_interface.model.domain.ResponseParameters;
import com.ledger.api_interface.model.dto.InterfaceInfo.InterfaceInfoCallRequest;
import com.ledger.api_interface.model.dto.InterfaceInfo.InterfaceInfoListSearchRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoAdminQueryListRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoQueryListRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoWithParams;
import com.ledger.api_interface.service.CallHistoryService;
import com.ledger.api_interface.service.InterfaceInfoService;
import com.ledger.api_interface.service.RequestParametersService;
import com.ledger.api_interface.service.ResponseParametersService;
import com.ledger.api_user.model.domain.UserInfo;
import com.ledger.api_user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.stream.Collectors;

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
        if (divide.compareTo(new BigDecimal(0)) < 0) {
            throw new KnowException("余额不足");
        }
        userByUsername.setAccount(divide);
        //调用次数加一
        interfaceInfoMapper.addCount(interfaceInfoCallRequest.getInterfaceId());
        userInfoService.updateById(userByUsername);
        Object jsonObject = "";
        String method = interfaceInfoCallRequest.getMethod();
        String url = interfaceInfoCallRequest.getUrl();
        HashMap<String, Object> params = interfaceInfoCallRequest.getParams();
        String respType = interfaceInfoCallRequest.getResp_type();
        if ("POST".equalsIgnoreCase(method)) {
            if ("JSON".equalsIgnoreCase(respType)) {
                jsonObject = HttpUtil.postJson(url, params, null);
            } else if ("IMAGE".equalsIgnoreCase(respType)) {
                byte[] bytes = HttpUtil.getByteArr(url, params, null);
                jsonObject = Base64.getEncoder().encodeToString(bytes);
            }
        } else if ("GET".equalsIgnoreCase(method)) {
            if ("JSON".equalsIgnoreCase(respType)) {
                jsonObject = HttpUtil.getJson(url, params, null);
            } else if ("IMAGE".equalsIgnoreCase(respType)) {
                byte[] bytes = HttpUtil.getByteArr(url, params, null);
                jsonObject = FileUtil.byteToBase64(bytes);
            }
        } else {
            throw new KnowException("不正确的调用方法");
        }
        String id = userByUsername.getId();
        if ("JSON".equalsIgnoreCase(respType)) {
            CallHistory callHistory = new CallHistory();
            callHistory.setUser_id(id);
            callHistory.setCall_time(LocalDateTime.now());
            callHistory.setInsterface_id(interfaceInfoCallRequest.getInterfaceId());
            callHistory.setResult(jsonObject.toString());
            callHistoryService.save(callHistory);
        }
        return Result.success(jsonObject);
    }

    @Override
    public Result<List<InterfaceInfoAdminQueryListRequest>> adminGetInterfaceList(InterfaceInfoListSearchRequest interfaceInfoCallRequest) {
        LambdaQueryWrapper<InterfaceInfo> wrapper = new LambdaQueryWrapper<>();
        String id = interfaceInfoCallRequest.getId();
        String name = interfaceInfoCallRequest.getName();
        int status = interfaceInfoCallRequest.getStatus();
        int consume = interfaceInfoCallRequest.getConsume();
        String method = interfaceInfoCallRequest.getMethod();

        wrapper.eq(StrUtil.isNotBlank(id), InterfaceInfo::getId, id);
        wrapper.eq(StrUtil.isNotBlank(name), InterfaceInfo::getName, name);
        wrapper.eq(status > -1, InterfaceInfo::getStatus, status);
        wrapper.eq(consume > -1, InterfaceInfo::getConsume, consume);
        wrapper.eq(StrUtil.isNotBlank(method), InterfaceInfo::getMethod, method);
        List<InterfaceInfo> list = list(wrapper);

        List<InterfaceInfoAdminQueryListRequest> collect =
                list.stream().map(i ->
                        BeanUtil.copyProperties(i, InterfaceInfoAdminQueryListRequest.class)).collect(Collectors.toList());
        return Result.success(collect);
    }


}




