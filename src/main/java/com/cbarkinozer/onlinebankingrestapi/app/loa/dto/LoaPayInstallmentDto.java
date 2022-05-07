package com.cbarkinozer.onlinebankingrestapi.app.loa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoaPayInstallmentDto {

    private Long loanId;
    private BigDecimal paymentAmount;
}
