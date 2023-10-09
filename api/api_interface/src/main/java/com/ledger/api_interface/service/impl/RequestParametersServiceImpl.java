package com.ledger.api_interface.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ledger.api_interface.model.domain.RequestParameters;
import com.ledger.api_interface.service.RequestParametersService;
import com.ledger.api_interface.mapper.RequestParametersMapper;
import org.springframework.stereotype.Service;

/**
* @author 22866
* @description 针对表【request_parameters】的数据库操作Service实现
* @createDate 2023-10-02 17:17:14
*/
@Service
public class RequestParametersServiceImpl extends ServiceImpl<RequestParametersMapper, RequestParameters>
    implements RequestParametersService{

}




