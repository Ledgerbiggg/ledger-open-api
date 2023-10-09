package com.ledger.api_common.model.enums;


public enum ErrorCode {
    // 示例错误代码和描述
    PARAMS_ERROR("100", "参数错误");

    private final String code;
    private final String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
