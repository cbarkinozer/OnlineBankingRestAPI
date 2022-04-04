package com.cbarkinozer.onlinebankingrestapi.app.acc.enums;

import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.BaseErrorMessage;

public enum AccErrorMessage implements BaseErrorMessage {
    ACCOUNT_NOT_FOUND("Account Not Found!","Please check the id of the account."),
    ;

    private  final String message;
    private  final String detailMessage;

    AccErrorMessage(String message, String detailMessage){
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
