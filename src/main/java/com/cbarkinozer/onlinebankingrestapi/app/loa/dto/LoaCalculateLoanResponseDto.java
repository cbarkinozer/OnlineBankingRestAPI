package com.cbarkinozer.onlinebankingrestapi.app.loa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoaCalculateLoanResponseDto {

    private BigDecimal interestRate;
    private BigDecimal totalInterest;
    private BigDecimal monthlyInstallmentAmount;
    private BigDecimal totalPayment;
    private BigDecimal annualCostRate;
    private BigDecimal allocationFee;

}
