package com.ledger.api_customerService.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ledger.api_common.Exception.KnowException;
import com.ledger.api_common.response.Result;
import com.ledger.api_common.util.JwtUtil;
import com.ledger.api_common.util.LocalDateTimeUtil;
import com.ledger.api_filterConfig.feign.userInfo.UserInfoService;
import com.ledger.api_filterConfig.model.domain.sessionInfo.SessionInfo;
import com.ledger.api_customerService.model.dto.SessionInfoDTO;
import com.ledger.api_customerService.model.vo.SessionInfoVo;
import com.ledger.api_customerService.service.SessionInfoService;
import com.ledger.api_customerService.mapper.SessionInfoMapper;
import com.ledger.api_filterConfig.model.domain.userInfo.UserInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 22866
 * @description 针对表【session_info】的数据库操作Service实现
 * @createDate 2023-10-29 14:48:29
 */
@Service
public class SessionInfoServiceImpl extends ServiceImpl<SessionInfoMapper, SessionInfo>
        implements SessionInfoService {


    @Resource
    private UserInfoService userInfoService;

    @Override
    public Result<SessionInfoVo> getAllSession() {
        List<SessionInfo> list = list();
        //这个是所有的信息的列表
        List<SessionInfoDTO> sessionInfoDTOList = list.stream().map(i -> {
            SessionInfoDTO sessionInfoDTO = BeanUtil.copyProperties(i, SessionInfoDTO.class);
            String dt = LocalDateTimeUtil.formatLocalDateTime(i.getDt());
            sessionInfoDTO.setFormatDt(dt);
            return sessionInfoDTO;
        }).collect(Collectors.toList());

        SessionInfoVo sessionInfoVo = new SessionInfoVo();

        for (SessionInfoDTO sessionInfoDTO : sessionInfoDTOList) {
            String username = sessionInfoDTO.getUser_id();
            List<SessionInfoDTO> sessionInfoDTOList1 = sessionInfoVo.get(username);
            if (sessionInfoDTOList1 == null) {
                ArrayList<SessionInfoDTO> sessionInfoDTOS = new ArrayList<>();
                sessionInfoDTOS.add(sessionInfoDTO);
                sessionInfoVo.put(username, sessionInfoDTOS);
            } else {
                sessionInfoDTOList1.add(sessionInfoDTO);
            }

        }
        return Result.success(sessionInfoVo);
    }

    @Override
    public Result<List<SessionInfoDTO>> getMySession(HttpServletRequest request) {
        LambdaQueryWrapper<SessionInfo> sessionInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        String username = JwtUtil.getClaimKeyUsernameFromRequest(request);
        if(username==null){
            throw new KnowException("未登录",401);
        }
        sessionInfoLambdaQueryWrapper.eq(SessionInfo::getUser_id, username);
        List<SessionInfo> list = list(sessionInfoLambdaQueryWrapper);

        List<SessionInfoDTO> collect = list.stream().map(i -> {
            SessionInfoDTO sessionInfoDTO = BeanUtil.copyProperties(i, SessionInfoDTO.class);
            String dt = LocalDateTimeUtil.formatLocalDateTime(i.getDt());
            sessionInfoDTO.setFormatDt(dt);
            return sessionInfoDTO;
        }).collect(Collectors.toList());

        return Result.success(collect);
    }

    @Override
    public void addASession(SessionInfo sessionInfo) {
        String userId = sessionInfo.getUser_id();
        UserInfo userByUsername = userInfoService.getUserByUsername(userId);
        String avatar = userByUsername.getAvatar();
        sessionInfo.setUser_icon(avatar);
        save(sessionInfo);
    }
}




