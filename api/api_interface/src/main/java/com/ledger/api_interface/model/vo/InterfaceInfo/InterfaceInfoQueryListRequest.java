package com.ledger.api_interface.model.vo.InterfaceInfo;

import lombok.Data;


@Data
public class InterfaceInfoQueryListRequest {
    private String id;
    private String name;
    private String description;
    private Integer call_count;
    private String img_url;

}
