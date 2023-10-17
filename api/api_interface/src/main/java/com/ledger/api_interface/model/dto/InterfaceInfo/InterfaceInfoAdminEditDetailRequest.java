package com.ledger.api_interface.model.dto.InterfaceInfo;

import lombok.Data;

import java.util.Date;

@Data
public class InterfaceInfoAdminEditDetailRequest {

    private String id;
    private String name;
    private int status;
    private int consume;
    private String method;
    private String description;
    private String url;
    private String img_url;
    private String resp_type;
    private String example;
    private Boolean need_certificate;

}
