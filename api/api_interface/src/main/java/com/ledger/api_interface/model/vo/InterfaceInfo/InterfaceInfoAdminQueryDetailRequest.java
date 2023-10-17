package com.ledger.api_interface.model.vo.InterfaceInfo;

import com.ledger.api_interface.model.vo.RequestParameters.RequestParametersVo;
import com.ledger.api_interface.model.vo.ResponseParameters.ResponseParametersVo;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class InterfaceInfoAdminQueryDetailRequest {

    private String id;
    private String name;
    private int status;
    private int consume;
    private String method;
    private String description;
    private String url;
    private Date update_time;
    private Date create_time;
    private String img_url;
    private Integer call_count;
    private String resp_type;
    private String example;
    private Boolean need_certificate;

    private List<RequestParametersVo> requestParametersVos;

    private List<ResponseParametersVo> responseParametersVos;

}
