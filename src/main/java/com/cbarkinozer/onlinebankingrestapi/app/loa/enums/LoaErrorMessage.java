package com.cbarkinozer.onlinebankingrestapi.app.loa.enums;

import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.BaseErrorMessage;

public enum LoaErrorMessage implements BaseErrorMessage {
    INTEREST_RATE_CANNOT_BE_NEGATIVE("Interest rate cannot be negative", "Check interest_rate constant or the api."),
    PARAMETER_CANNOT_BE_NULL("Parameter cannot be null","Please be sure you entered all parameters."),
    INSTALLMENT_AMOUNT_MUST_BE_POSITIVE("Installment amount must be positive","Please be sure that you entered no negative amounts."),
    TOTAL_LOAN_PAYMENT_MUST_BE_POSITIVE("Total loan payment must be positive","Please be sure that you entered you no negative loan."),
    LATE_FEE_RATE_CANNOT_BE_NEGATIVE("Late fee rate cannot be negative","Calculations went wrong. The result is not correct."),
    TOTAL_LATE_FEE_MUST_BE_POSITIVE("Total late fee must be positive","Please be sure that you entered a valid positive late day count."),
    LATE_INTEREST_TAX_CANNOT_BE_NEGATIVE("Late interest tax cannot be negative","Please be sure that you entered no negative total late fee."),
    LOAN_AMOUNT_CANNOT_BE_GREATER_THAN_MAX_AMOUNT("Loan amount cannot be greater than max loan amount."
            ,"you can not enter an amount larger than the max amount(calculated according to given values):"),
    ;

    private  final String message;
    private  String detailMessage;

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

    public void setDetailMessage(String extraDetail){
        detailMessage = detailMessage+" "+extraDetail;
    }

}
