package com.ledger.api_interface.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ledger.api_common.model.query.PageQuery;
import com.ledger.api_common.response.Result;
import com.ledger.api_common.util.PageUtil;
import com.ledger.api_interface.model.domain.CallHistory;
import com.ledger.api_interface.model.dto.callHistory.CallHistoryQueryRequest;
import com.ledger.api_interface.service.CallHistoryService;
import com.ledger.api_interface.mapper.CallHistoryMapper;
import com.ledger.api_user.model.domain.UserInfo;
import com.ledger.api_user.service.UserInfoService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * @author 22866
 * @description 针对表【call_history】的数据库操作Service实现
 * @createDate 2023-10-03 16:49:06
 */
@Service
public class CallHistoryServiceImpl extends ServiceImpl<CallHistoryMapper, CallHistory>
        implements CallHistoryService {


    @Resource
    private UserInfoService userInfoService;

    @Resource
    private CallHistoryMapper callHistoryMapper;


    @Override
    public Result<Page<CallHistoryQueryRequest>> callHistory(PageQuery pageQuery, String interfaceId) {

        LambdaQueryWrapper<CallHistory> wrapper = new LambdaQueryWrapper<>();

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserInfo userByUsername = userInfoService.getUserByUsername(userDetails.getUsername());

        wrapper.eq(CallHistory::getUser_id, userByUsername.getId());
        wrapper.eq(CallHistory::getInsterface_id, interfaceId);
        wrapper.orderByDesc(CallHistory::getCall_time);

        Page<CallHistory> pageFromPageQuery = PageUtil.getPageFromPageQuery(pageQuery, CallHistory.class);

        Page<CallHistory> callHistoryPage = callHistoryMapper.selectPage(pageFromPageQuery, wrapper);

        Page<CallHistoryQueryRequest> recordsBySource = PageUtil.getRecordsBySource(callHistoryPage, CallHistoryQueryRequest.class, count(wrapper));

        recordsBySource.getRecords().forEach(i->{
            LocalDateTime callTime = i.getCall_time();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String format = callTime.format(dateTimeFormatter);
            i.setTime(format);
            i.setCall_time(null);
        });

        return Result.success(recordsBySource);
    }
}




