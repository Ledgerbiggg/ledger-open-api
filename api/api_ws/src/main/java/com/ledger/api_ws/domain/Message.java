package com.ledger.api_ws.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {


    private boolean kk;
    private String fromName;
    private String message;
    private String toName;
    private String formatDt;

}
