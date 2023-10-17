package com.ledger.api_common.enums;

public enum Method {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String method;

    Method(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
