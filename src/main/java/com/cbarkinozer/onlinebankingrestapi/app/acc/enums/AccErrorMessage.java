package com.cbarkinozer.onlinebankingrestapi.app.acc.enums;

import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.BaseErrorMessage;

public enum AccErrorMessage implements BaseErrorMessage {
    ACCOUNT_NOT_FOUND("Account Not Found!"
            ,"Please check the id of the account."),
    INSUFFICIENT_BALANCE("Balance Is Insufficient!"
            ,"Please check your balance and the amount of money you want to send."),
    IBAN_NO_IS_NOT_UNIQUE("IBAN Number Is Not Unique!"
            ,"Please check if the IBAN number you entered is correct."),
    FIELD_CANNOT_BE_NULL("Field cannot be null"
            ,"Some of the fields are null, please be sure to enter all fields"),
    BALANCE_CANNOT_BE_NEGATIVE("Balance cannot assigned negative"
            ,"Please be sure that you entered a balance that is not negative"),
    AMOUNT_MUST_BE_POSITIVE("Amount must be positive"
            ,"Please be sure that the entered amount value is larger than zero"),
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
