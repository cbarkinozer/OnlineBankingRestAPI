package com.cbarkinozer.onlinebankingrestapi.app.acc.dto;

import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccMoneyTransferType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccMoneyTransferDto {

    private Long accountIdFrom;
    private Long accountIdTo;
    private BigDecimal amount;
    private Date transferDate;
    private String description;
    private AccMoneyTransferType transferType;
}
