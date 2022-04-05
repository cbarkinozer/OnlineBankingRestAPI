package com.cbarkinozer.onlinebankingrestapi.app.acc.dto;

import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccAccountType;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccCurrencyType;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenStatusType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccAccountDto {

    private Long id;
    private Long customerId;
    private String ibanNo;
    private BigDecimal currentBalance;
    private AccCurrencyType currencyType;
    private AccAccountType accountType;
    private GenStatusType statusType;
}