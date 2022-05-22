package com.cbarkinozer.onlinebankingrestapi.app.loa.dto;

import com.cbarkinozer.onlinebankingrestapi.app.loa.enums.LoaLoanStatusType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoaLoanDto {

    private Long id;
    private Long customerId;
    private Integer installmentCount;
    private BigDecimal principalLoanAmount;
    private BigDecimal monthlyInstallmentAmount;
    private BigDecimal interestToBePaid;
    private BigDecimal principalToBePaid;
    private BigDecimal remainingPrincipal;
    private LocalDate dueDate;
    private LoaLoanStatusType loanStatusType;

}
