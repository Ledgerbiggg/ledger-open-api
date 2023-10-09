package com.ledger.api_interface.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledger.api_common.model.query.PageQuery;
import com.ledger.api_common.response.Result;
import com.ledger.api_interface.model.domain.CallHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ledger.api_interface.model.dto.callHistory.CallHistoryQueryRequest;
import com.ledger.api_interface.model.vo.InterfaceInfo.InterfaceInfoQueryListRequest;

/**
* @author 22866
* @description 针对表【call_history】的数据库操作Service
* @createDate 2023-10-03 16:49:06
*/
public interface CallHistoryService extends IService<CallHistory> {


    Result<Page<CallHistoryQueryRequest>> callHistory(PageQuery pageQuery,String interfaceId);
}
