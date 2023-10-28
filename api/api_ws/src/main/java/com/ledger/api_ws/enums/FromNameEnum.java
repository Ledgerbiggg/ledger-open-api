package com.ledger.api_ws.enums;

public enum FromNameEnum {
    CUSTOMER_SERVICE("kk"), SYSTEM("system");
    private final String name;

    FromNameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
