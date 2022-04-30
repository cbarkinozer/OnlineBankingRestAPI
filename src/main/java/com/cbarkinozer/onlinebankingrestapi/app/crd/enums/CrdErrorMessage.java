package com.cbarkinozer.onlinebankingrestapi.app.crd.enums;

import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.BaseErrorMessage;

public enum CrdErrorMessage implements BaseErrorMessage {
    PARAMETER_MIN_CANNOT_BE_LARGER_THAN_MAX("Parameter min cannot be larger than max","Please be sure that parameter min is not larger than max"),
    CUT_OFF_DAY_IS_NOT_VALID("Cut off day is not valid","Please be sure that the cut off day is between [1,31]."),
    INSUFFICIENT_CREDIT_CARD_LIMIT("Insufficient credit card limit","You have reached your card's limit."),
    INVALID_CREDIT_CARD("Invalid credit card.","Credit card is not found."),
    CREDIT_CARD_EXPIRED("Credit card is expired","Please check your credit cards expire date."),
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

