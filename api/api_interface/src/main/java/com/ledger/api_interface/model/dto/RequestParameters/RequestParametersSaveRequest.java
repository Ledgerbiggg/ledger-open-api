package com.ledger.api_interface.model.dto.RequestParameters;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RequestParametersSaveRequest {


    private String id;

    /**
     * 参数名称
     */
    @NotBlank(message = "参数名称不能为空")
    private String name;

    /**
     * 参数是否必选，1表示是，0表示否
     */
    @NotBlank(message = "是否必须不能为空")
    private Integer is_required;

    /**
     * 参数的数据类型
     */
    @NotBlank(message = "类型不能为空")
    private String type;

    /**
     * 参数的描述信息
     */
    @NotBlank(message = "描述不能为空")
    private String description;


    @NotBlank(message = "默认值不能为空")
    private String default_value;

}
