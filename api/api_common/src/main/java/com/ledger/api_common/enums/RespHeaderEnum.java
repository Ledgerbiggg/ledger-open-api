package com.ledger.api_common.enums;

public enum RespHeaderEnum {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    CACHE_CONTROL("Cache-Control"),
    SERVER("Server"),
    DATE("Date"),
    LOCATION("Location"),
    SET_COOKIE("Set-Cookie"),
    LAST_MODIFIED("Last-Modified"),
    ETAG("ETag"),
    ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin");

    private final String headerName;

    RespHeaderEnum(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderName() {
        return headerName;
    }
}
