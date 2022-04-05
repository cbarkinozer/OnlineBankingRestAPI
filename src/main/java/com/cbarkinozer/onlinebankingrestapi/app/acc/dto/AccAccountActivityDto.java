package com.cbarkinozer.onlinebankingrestapi.app.acc.dto;

import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccAccountActivityType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AccAccountActivityDto {

    private Long accountId;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private BigDecimal currentBalance;
    private AccAccountActivityType accountActivityType;
}
