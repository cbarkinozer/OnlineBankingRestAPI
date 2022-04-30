package com.cbarkinozer.onlinebankingrestapi.app.crd.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CrdCreditCardSpendDto {

    private Long cardNo;
    private Long cvvNo;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate expireDate;
    private BigDecimal amount;
    private String description;

}
