package com.cbarkinozer.onlinebankingrestapi.app.gen.enums;

public enum GenErrorMessage implements BaseErrorMessage{

    ITEM_NOT_FOUND("Item not found!"),
    DATE_COULD_NOT_BE_CONVERTED("Date could not be converted!"),
    VALUE_CANNOT_BE_NEGATIVE("Value cannot be negative!"),
    PARAMETER_CANNOT_BE_NULL("Parameter cannot be null"),
    ;

    private String message;

    GenErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
