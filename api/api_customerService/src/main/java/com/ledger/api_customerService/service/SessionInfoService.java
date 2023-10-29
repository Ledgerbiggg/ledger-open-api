package com.ledger.api_customerService.service;

import com.ledger.api_common.response.Result;
import com.ledger.api_filterConfig.model.domain.sessionInfo.SessionInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ledger.api_customerService.model.dto.SessionInfoDTO;
import com.ledger.api_customerService.model.vo.SessionInfoVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 22866
* @description 针对表【session_info】的数据库操作Service
* @createDate 2023-10-29 14:48:29
*/
public interface SessionInfoService extends IService<SessionInfo> {

    Result<SessionInfoVo> getAllSession();

    Result<List<SessionInfoDTO>> getMySession(HttpServletRequest request);

}
