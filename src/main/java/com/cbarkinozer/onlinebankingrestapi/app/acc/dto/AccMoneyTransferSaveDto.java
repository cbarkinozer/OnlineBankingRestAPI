package com.cbarkinozer.onlinebankingrestapi.app.acc.dto;

import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccMoneyTransferType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccMoneyTransferSaveDto {

    private Long accountIdFrom;
    private Long accountIdTo;
    private BigDecimal amount;
    private String description;
    private AccMoneyTransferType transferType;
}
