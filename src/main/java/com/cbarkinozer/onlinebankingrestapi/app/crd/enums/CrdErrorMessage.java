package com.cbarkinozer.onlinebankingrestapi.app.crd.enums;

import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.BaseErrorMessage;

public enum CrdErrorMessage implements BaseErrorMessage {
    PARAMETER_MIN_CANNOT_BE_LARGER_THAN_MAX("Parameter min cannot be larger than max","Please be sure that parameter min is not larger than max"),
    ;

    private final String message;
    private final String detailMessage;

    CrdErrorMessage(String message, String detailMessage) {
        this.message = message;
        this.detailMessage = detailMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getDetailMessage() {
        return detailMessage;
    }
}

