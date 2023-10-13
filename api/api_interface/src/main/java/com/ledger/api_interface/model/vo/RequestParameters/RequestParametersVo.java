package com.ledger.api_interface.model.vo.RequestParameters;

import lombok.Data;

@Data
public class RequestParametersVo {

    private String id;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数是否必选，1表示是，0表示否
     */
    private Integer is_required;

    /**
     * 参数的数据类型
     */
    private String type;

    /**
     * 参数的描述信息
     */
    private String description;


    private String default_value;

}
