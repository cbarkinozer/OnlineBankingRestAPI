package com.cbarkinozer.onlinebankingrestapi.app.loa.enums;

import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.BaseErrorMessage;

public enum LoaErrorMessage implements BaseErrorMessage {

    INTEREST_RATE_CANNOT_BE_NEGATIVE("Interest rate cannot be negative"
            , "Check interest_rate constant or the api."),
    PARAMETER_CANNOT_BE_NULL("Parameter cannot be null"
            ,"Please be sure you entered all parameters."),
    INSTALLMENT_AMOUNT_MUST_BE_POSITIVE("Installment amount must be positive"
            ,"Please be sure that you entered no negative amounts."),
    TOTAL_PAYMENT_MUST_BE_POSITIVE("Total payment must be positive"
            ,"Please be sure that you entered no negative loan."),
    LATE_FEE_RATE_CANNOT_BE_NEGATIVE("Late fee rate cannot be negative"
            ,"Calculations went wrong. The result is not correct."),
    TOTAL_LATE_FEE_MUST_BE_POSITIVE("Total late fee must be positive"
            ,"Please be sure that you entered a valid positive late day count."),
    LATE_INTEREST_TAX_CANNOT_BE_NEGATIVE("Late interest tax cannot be negative"
            ,"Please be sure that you entered no negative total late fee."),
    LOAN_AMOUNT_CANNOT_BE_GREATER_THAN_MAX_AMOUNT("Loan amount cannot be greater than max loan amount."
            ,"you can not enter an amount larger than the max amount(calculated according to given values):"),
    CUSTOMER_NOT_FOUND("Customer not found"
            ,"Please check customerId for loan."),
    MONTHLY_INSTALLMENT_AMOUNT_MUST_BE_POSITIVE("Monthly installment amount must be positive"
            ,"Please be sure that you entered installment count positive."),
    INTEREST_AMOUNT_CANNOT_BE_NEGATIVE("Interest amount cannot be negative"
            ,"Calculations went wrong. The result is not correct."),
    PRINCIPAL_lOAN_AMOUNT_MUST_BE_POSITIVE("Principal loan amount must be positive"
            ,"Please be sure that you entered principal loan amount positive."),
    LOAN_AMOUNT_NOT_ENOUGH_TO_PAY_OFF("Loan amount is not enough to pay off the loan"
            ,"Please pay as installment or change the amount. Remaining amount to pay off :"),
    DUE_DATE_HAS_NOT_PASSED_YET("Due date has not passed yet"
            ,"There is still time for the due date :"),
    REMAINING_PRINCIPAL_MUST_BE_POSITIVE("Remaining principal must be positive",
            "Calculations went wrong. The result is not correct." ),
    TAX_RATE_CANNOT_BE_NEGATIVE("Tax rate cannot be negative"
            , "Please check tax rate." ),
    INSTALLMENT_COUNT_CANNOT_BE_LARGER_THAN_LIMIT("Installment count cannot be larger than limit"
            , "Please enter an installment count that is smaller than the limit:" ),
    LOAN_ALREADY_PAID_OFF("Loan is already paid off"
            , "Please be sure that you entered correct loan id." ),
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
