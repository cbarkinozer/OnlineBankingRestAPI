package com.cbarkinozer.onlinebankingrestapi.app.loa.enums;

import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.BaseErrorMessage;

public enum LoaErrorMessage implements BaseErrorMessage {
    INTEREST_RATE_CANNOT_BE_NEGATIVE("Interest rate cannot be negative", "Check interest_rate constant or the api."),
    PARAMETER_CANNOT_BE_NULL("Parameter cannot be null","Please be sure you entered all parameters."),
    INSTALLMENT_AMOUNT_MUST_BE_POSITIVE("Installment amount must be positive","Please be sure that you entered no negative amounts."),
    TOTAL_LOAN_PAYMENT_MUST_BE_POSITIVE("Total loan payment must be positive","Please be sure that you entered you no negative loan."),
    ;

    private  final String message;
    private  final String detailMessage;

    LoaErrorMessage(String message, String detailMessage){
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
