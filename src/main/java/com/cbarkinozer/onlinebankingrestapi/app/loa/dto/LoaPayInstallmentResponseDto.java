package com.cbarkinozer.onlinebankingrestapi.app.loa.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoaPayInstallmentResponseDto {

    private Long loanId;
    private BigDecimal paymentAmount;
    private LocalDate PaymentDate;
    private BigDecimal interestToBePaid;
    private BigDecimal principalToBePaid;
    private BigDecimal remainingPrincipal;
    private LocalDate dueDate;
}
