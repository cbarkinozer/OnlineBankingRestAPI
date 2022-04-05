package com.cbarkinozer.onlinebankingrestapi.app.sec.enums;

public enum EnumJwtConstant {
    BEARER("Bearer ")
    ;

    private final String constant;
    EnumJwtConstant(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }

    @Override
    public String toString() {
        return constant;
    }
}
