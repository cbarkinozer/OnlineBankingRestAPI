package com.cbarkinozer.onlinebankingrestapi.app.crd.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CrdCreditCardDto {

    private Long id;
    private Long cusCustomerId;
    private Long cardNo;
    private Long cvvNo;
    private LocalDate expireDate;
    private BigDecimal totalLimit;
    private BigDecimal availableCardLimit;
    private BigDecimal currentDebt;
    private BigDecimal minimumPaymentAmount;
    private LocalDate cutoffDate;
    private LocalDate dueDate;
    private LocalDate cancelDate;
}
