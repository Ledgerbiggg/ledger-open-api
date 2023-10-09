package com.ledger.api_interface.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ledger.api_interface.model.domain.ResponseParameters;
import com.ledger.api_interface.service.ResponseParametersService;
import com.ledger.api_interface.mapper.ResponseParametersMapper;
import org.springframework.stereotype.Service;

/**
* @author 22866
* @description 针对表【response_parameters】的数据库操作Service实现
* @createDate 2023-10-02 17:31:09
*/
@Service
public class ResponseParametersServiceImpl extends ServiceImpl<ResponseParametersMapper, ResponseParameters>
    implements ResponseParametersService{

}




