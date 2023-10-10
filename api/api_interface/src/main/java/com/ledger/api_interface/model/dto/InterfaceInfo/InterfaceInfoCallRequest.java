package com.ledger.api_interface.model.dto.InterfaceInfo;

import lombok.Data;

import java.util.HashMap;

@Data
public class InterfaceInfoCallRequest {

    String url;
    String method;
    String interfaceId;
    String resp_type;

    HashMap<String, Object> params;


}
