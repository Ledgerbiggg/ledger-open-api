package com.ledger.api_interface.model.dto.InterfaceInfo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class InterfaceInfoListSearchRequest {

    private String id;
    private String name;
    private int status=-1;
    private int consume=-1;
    private String method;



}
