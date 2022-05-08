package com.cbarkinozer.onlinebankingrestapi.app.loa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoaPayOffDto {

    private Long loanId;
    private BigDecimal paymentAmount;
}
