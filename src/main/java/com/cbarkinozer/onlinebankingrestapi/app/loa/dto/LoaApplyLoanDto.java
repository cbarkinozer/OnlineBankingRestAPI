package com.cbarkinozer.onlinebankingrestapi.app.loa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoaApplyLoanDto {

    private Integer installmentCount;
    private BigDecimal principalLoanAmount;
    private BigDecimal monthlySalary;

}
