package com.ledger.api_common.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LedgerException extends RuntimeException{
    private String message;
    private Integer code;
    public LedgerException(String message) {
        this.message = message;
    }
}

