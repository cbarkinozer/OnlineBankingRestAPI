package com.cbarkinozer.onlinebankingrestapi.app.loa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoaCalculateLoanDto {

    private Integer installmentCount;
    private BigDecimal principalLoanAmount;

}
