package com.cbarkinozer.onlinebankingrestapi.app.loa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoaCalculateLoanResponseDto {

    private BigDecimal interestRate;
    private BigDecimal monthlyInstallmentAmount;
    private BigDecimal totalLoanPayment;
}
