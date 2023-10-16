package com.ledger.api_interface.model.dto.InterfaceInfo;

import com.ledger.api_interface.model.dto.RequestParameters.RequestParametersSaveRequest;
import com.ledger.api_interface.model.dto.ResponseParameters.ResponseParametersSaveRequest;
import lombok.Data;

import java.util.List;

@Data
public class InterfaceInfoAdminSaveDetailRequest {

    private int consume;
    private String description;
    private String example;
    private String img_url;
    private String method;
    private String name;
    private String resp_type;
    private int status;
    private String url;

    private List<RequestParametersSaveRequest> requestParametersSaveRequests;

    private List<ResponseParametersSaveRequest> responseParametersSaveRequests;











}
