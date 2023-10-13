package com.ledger.api_interface.model.vo.ResponseParameters;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ResponseParametersVo {

    private String id;

    private String name;


    private String type;


    private String description;
}
