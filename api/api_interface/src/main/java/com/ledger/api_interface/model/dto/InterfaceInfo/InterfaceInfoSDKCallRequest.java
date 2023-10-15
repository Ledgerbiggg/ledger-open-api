package com.ledger.api_interface.model.dto.InterfaceInfo;

import lombok.Data;

import java.util.HashMap;

@Data
public class InterfaceInfoSDKCallRequest {
    String method;
    String resp_type;

    HashMap<String, Object> params;

}
