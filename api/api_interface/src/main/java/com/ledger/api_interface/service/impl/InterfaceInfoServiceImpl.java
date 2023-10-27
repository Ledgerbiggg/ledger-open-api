package com.ledger.api_interface.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

import java.io.*;
import java.util.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ledger.api_common.Exception.KnowException;
import com.ledger.api_common.model.query.PageQuery;
import com.ledger.api_common.model.vo.UploadVo;
import com.ledger.api_common.response.Result;
import com.ledger.api_common.util.*;
import com.ledger.api_filterConfig.feign.userInfo.UserInfoService;
import com.ledger.api_filterConfig.model.domain.userInfo.UserInfo;
import com.ledger.api_interface.mapper.InterfaceInfoMapper;
import com.ledger.api_interface.model.domain.CallHistory;
import com.ledger.api_interface.model.domain.InterfaceInfo;
import com.ledger.api_interface.model.domain.RequestParameters;
import com.ledger.api_interface.model.domain.ResponseParameters;
import com.ledger.api_interface.model.dto.InterfaceInfo.*;
import com.ledger.api_interface.model.dto.RequestParameters.RequestParametersRequest;
import com.ledger.api_interface.model.dto.RequestParameters.RequestParametersSaveRequest;
import com.ledger.api_interface.model.dto.ResponseParameters.ResponseParametersRequest;
import com.ledger.api_interface.model.dto.ResponseParameters.ResponseParametersSaveRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoAdminQueryDetailRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoAdminQueryListRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoQueryListRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoWithParams;
import com.ledger.api_interface.model.vo.RequestParameters.RequestParametersVo;
import com.ledger.api_interface.model.vo.ResponseParameters.ResponseParametersVo;
import com.ledger.api_interface.service.CallHistoryService;
import com.ledger.api_interface.service.InterfaceInfoService;
import com.ledger.api_interface.service.RequestParametersService;
import com.ledger.api_interface.service.ResponseParametersService;

import com.ledger.api_common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${sys.profile}")
    private String profile;

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

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Value("${address}")
    private String address;


    @Override
    public Result<Page<InterfaceInfoQueryListRequest>> getInterfaceList(PageQuery pageQuery, String search) {
        LambdaQueryWrapper<InterfaceInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InterfaceInfo::getStatus, 1);
        wrapper.eq(InterfaceInfo::getIs_delete, 0);
        wrapper.like(StrUtil.isNotBlank(search), InterfaceInfo::getName, search);
        wrapper.like(StrUtil.isNotBlank(search), InterfaceInfo::getDescription, search);
        wrapper.orderByDesc(InterfaceInfo::getCall_count);
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

        interfaceInfoWithParams.setUrl(address + "/" + interfaceInfo.getId());
        return Result.success(interfaceInfoWithParams);
    }

    @Override
    public Result<Object> call(InterfaceInfoCallRequest interfaceInfoCallRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        UserInfo userByUsername = userInfoService.getUserByUsername(username);
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(interfaceInfoCallRequest.getInterfaceId());
        String url = interfaceInfo.getUrl();
        Boolean needCertificate = interfaceInfo.getNeed_certificate();
        BigDecimal divide = userByUsername.getAccount().subtract(new BigDecimal(interfaceInfo.getConsume()));
        if (divide.compareTo(new BigDecimal(0)) < 0) {
            throw new KnowException("余额不足");
        }
        userByUsername.setAccount(divide);
        //调用次数加一
        interfaceInfoMapper.addCount(interfaceInfoCallRequest.getInterfaceId());
        userInfoService.updateById(userByUsername);
        Object jsonObject = "";
        String method = interfaceInfoCallRequest.getMethod();
        HashMap<String, Object> params = interfaceInfoCallRequest.getParams();
        String respType = interfaceInfoCallRequest.getResp_type();
        if ("POST".equalsIgnoreCase(method)) {
            if ("JSON".equalsIgnoreCase(respType)) {
                try {
                    jsonObject = HttpUtil.postJson(url, params, null, needCertificate);
                } catch (JSONException e) {
                    jsonObject = HttpUtil.post(url, params, null, needCertificate);
                }
            } else if ("IMAGE".equalsIgnoreCase(respType)) {
                byte[] bytes = HttpUtil.getByteArr(url, params, null, needCertificate);
                jsonObject = Base64.getEncoder().encodeToString(bytes);
            }
        } else if ("GET".equalsIgnoreCase(method)) {
            if ("JSON".equalsIgnoreCase(respType)) {
                try {
                    jsonObject = HttpUtil.getJson(url, params, null, needCertificate);
                } catch (JSONException e) {
                    jsonObject = HttpUtil.post(url, params, null, needCertificate);
                }
            } else if ("IMAGE".equalsIgnoreCase(respType)) {
                byte[] bytes = HttpUtil.getByteArr(url, params, null, needCertificate);
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

    @Override
    public Result<InterfaceInfoAdminQueryDetailRequest> getInterfaceDetailById(String id) {

        InterfaceInfo interfaceInfo = getById(id);

        InterfaceInfoAdminQueryDetailRequest interfaceInfoAdminQueryDetailRequest =
                BeanUtil.copyProperties(interfaceInfo, InterfaceInfoAdminQueryDetailRequest.class);

        LambdaQueryWrapper<RequestParameters> requestParametersLambdaQueryWrapper = new LambdaQueryWrapper<>();

        requestParametersLambdaQueryWrapper.eq(RequestParameters::getApi_id, id);

        List<RequestParameters> requestParameters = requestParametersService.list(requestParametersLambdaQueryWrapper);

        LambdaQueryWrapper<ResponseParameters> responseParametersLambdaQueryWrapper = new LambdaQueryWrapper<>();

        responseParametersLambdaQueryWrapper.eq(ResponseParameters::getApi_id, id);

        List<ResponseParameters> responseParameters = responseParametersService.list(responseParametersLambdaQueryWrapper);


        List<RequestParametersVo> requestParametersVos = requestParameters.stream().map(i -> BeanUtil.copyProperties(i, RequestParametersVo.class)).collect(Collectors.toList());
        List<ResponseParametersVo> responseParametersVos = responseParameters.stream().map(i -> BeanUtil.copyProperties(i, ResponseParametersVo.class)).collect(Collectors.toList());


        interfaceInfoAdminQueryDetailRequest.setRequestParametersVos(requestParametersVos);
        interfaceInfoAdminQueryDetailRequest.setResponseParametersVos(responseParametersVos);

        return Result.success(interfaceInfoAdminQueryDetailRequest);

    }

    @Override
    public Result<Object> externalCall(InterfaceInfoSDKCallRequest interfaceInfoSDKCallRequest, HttpServletRequest request, String id) {
        String accessKey = request.getHeader("AccessKey");
        String secretKey = request.getHeader("SecretKey");
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInfo::getAccessKey, accessKey);
        wrapper.eq(UserInfo::getSecretKey, secretKey);
        UserInfo userInfo = userInfoService.getOne(wrapper);
        if (userInfo == null) {
            throw new KnowException("错误的AccessKey或者错误的SecretKey");
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        String url = interfaceInfo.getUrl();
        Boolean needCertificate = interfaceInfo.getNeed_certificate();
        BigDecimal divide = userInfo.getAccount().subtract(new BigDecimal(interfaceInfo.getConsume()));
        if (divide.compareTo(new BigDecimal(0)) < 0) {
            throw new KnowException("余额不足");
        }
        userInfo.setAccount(divide);
        //调用次数加一
        interfaceInfoMapper.addCount(id);
        userInfoService.updateById(userInfo);
        Object jsonObject = "";
        String method = interfaceInfoSDKCallRequest.getMethod();
        HashMap<String, Object> params = interfaceInfoSDKCallRequest.getParams();
        String respType = interfaceInfoSDKCallRequest.getResp_type();
        if ("POST".equalsIgnoreCase(method)) {
            if ("JSON".equalsIgnoreCase(respType)) {
                jsonObject = HttpUtil.postJson(url, params, null, needCertificate);
            } else if ("IMAGE".equalsIgnoreCase(respType)) {
                byte[] bytes = HttpUtil.getByteArr(url, params, null, needCertificate);
                jsonObject = Base64.getEncoder().encodeToString(bytes);
            } else {
                throw new KnowException("不正确的返回结果格式");
            }
        } else if ("GET".equalsIgnoreCase(method)) {
            if ("JSON".equalsIgnoreCase(respType)) {
                jsonObject = HttpUtil.getJson(url, params, null, needCertificate);
            } else if ("IMAGE".equalsIgnoreCase(respType)) {
                byte[] bytes = HttpUtil.getByteArr(url, params, null, needCertificate);
                jsonObject = FileUtil.byteToBase64(bytes);
            } else {
                throw new KnowException("不正确的返回结果格式");
            }
        } else {
            throw new KnowException("不正确的调用方法");
        }
        String userId = userInfo.getId();
        if ("JSON".equalsIgnoreCase(respType)) {
            CallHistory callHistory = new CallHistory();
            callHistory.setUser_id(userId);
            callHistory.setCall_time(LocalDateTime.now());
            callHistory.setInsterface_id(id);
            callHistory.setResult(jsonObject.toString());
            callHistoryService.save(callHistory);
        }
        return Result.success(jsonObject);
    }

    @Override
    public Result<String> modifyInterfaceRequestParameters(RequestParametersRequest requestParametersVo) {
        RequestParameters requestParameters = BeanUtil.copyProperties(requestParametersVo, RequestParameters.class);
        requestParametersService.saveOrUpdate(requestParameters);
        return Result.success("操作成功");
    }

    @Override
    public Result<String> modifyInterfaceResponseParameters(ResponseParametersRequest responseParametersRequest) {
        ResponseParameters responseParameters = BeanUtil.copyProperties(responseParametersRequest, ResponseParameters.class);
        responseParametersService.saveOrUpdate(responseParameters);
        return Result.success("操作成功");
    }

    @Override
    public Result<String> modifyInterfaceParameters(InterfaceInfoAdminEditDetailRequest interfaceInfoAdminEditDetailRequest) {
        InterfaceInfo interfaceInfo = BeanUtil.copyProperties(interfaceInfoAdminEditDetailRequest, InterfaceInfo.class);

        String example = "";
        if (interfaceInfo.getResp_type().equals("JSON")) {
            example = JSON.parse(interfaceInfoAdminEditDetailRequest.getExample()).toString();
        } else if (interfaceInfo.getResp_type().equals("IMAGE")) {
            example = interfaceInfo.getExample();
        }
        interfaceInfo.setExample(example);
        saveOrUpdate(interfaceInfo);
        return Result.success("操作成功");
    }

    @Override
    public Result<String> saveInterfaceParameters(InterfaceInfoAdminSaveDetailRequest interfaceInfoAdminSaveDetailRequest) {

        InterfaceInfo interfaceInfo = BeanUtil.copyProperties(interfaceInfoAdminSaveDetailRequest, InterfaceInfo.class);
        String interfaceId = UUIDUtil.getUUID();
        interfaceInfo.setId(interfaceId);
        save(interfaceInfo);

        List<RequestParametersSaveRequest> requestParametersSaveRequests =
                interfaceInfoAdminSaveDetailRequest.getRequestParametersSaveRequests();

        List<RequestParameters> reqList =
                requestParametersSaveRequests
                        .stream()
                        .map(i -> {
                            RequestParameters requestParameters = BeanUtil.copyProperties(i, RequestParameters.class);
                            requestParameters.setApi_id(interfaceId);
                            requestParameters.setId(UUIDUtil.getUUID());
                            return requestParameters;
                        })
                        .collect(Collectors.toList());

        List<ResponseParametersSaveRequest> responseParametersSaveRequests =
                interfaceInfoAdminSaveDetailRequest.getResponseParametersSaveRequests();

        List<ResponseParameters> respList =
                responseParametersSaveRequests
                        .stream()
                        .map(i -> {
                            ResponseParameters responseParameters = BeanUtil.copyProperties(i, ResponseParameters.class);
                            responseParameters.setApi_id(interfaceId);
                            responseParameters.setId(UUIDUtil.getUUID());
                            return responseParameters;
                        })
                        .collect(Collectors.toList());

        requestParametersService.saveBatch(reqList);

        responseParametersService.saveBatch(respList);

        return Result.success("操作成功");
    }

    @Override
    public Result<UploadVo> uploadFile(MultipartFile file) {
        String uuid = UUIDUtil.getUUID();

        String nameWithFileExtension = FileUtil.uploadFile(file, uuid, profile);

        UploadVo uploadVo = new UploadVo();

        String name = nameWithFileExtension.substring(0, nameWithFileExtension.lastIndexOf("."));
        String fileExtension = nameWithFileExtension.substring(nameWithFileExtension.lastIndexOf(".") + 1);

        uploadVo.setName(name);
        uploadVo.setFileExtension(fileExtension);
        uploadVo.setNameWithFileExtension(nameWithFileExtension);

        return Result.success(uploadVo);
    }

    @Override
    public Result<String> getFileCheck() {
        String token =
                JwtUtil.createTempJwt((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal(), null, secret);
        return Result.success(token);
    }

    @Override
    public void getFile(String fileName, String token, HttpServletResponse response) {
        boolean b = JwtUtil.validateJwt(token, secret);
        if (!b) {
            throw new KnowException("token验证失败");
        }
        response.setContentType("image/jpeg");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            byte[] bytes = FileUtil.downloadFile(fileName, profile);
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                //关闭流
                assert outputStream != null;
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}




