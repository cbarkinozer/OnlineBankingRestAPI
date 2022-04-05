package com.cbarkinozer.onlinebankingrestapi.app.acc.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccMoneyActivityRequestDto {

    private Long accountId;
    private BigDecimal amount;

}
