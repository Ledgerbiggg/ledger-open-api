package com.ledger.api_interface.model.dto.ResponseParameters;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResponseParametersSaveRequest {

    private String id;

    @NotBlank(message = "参数名称不能为空")
    private String name;


    @NotBlank(message = "是否必须不能为空")
    private String type;


    @NotBlank(message = "描述不能为空")
    private String description;
}
