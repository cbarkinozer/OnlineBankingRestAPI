package com.cbarkinozer.onlinebankingrestapi.app.acc.dto;

import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccMoneyTransferType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class AccMoneyTransferDto {

    private Long accountIdFrom;
    private Long accountIdTo;
    private BigDecimal amount;
    private LocalDate transferDate;
    private String description;
    private AccMoneyTransferType transferType;
}
