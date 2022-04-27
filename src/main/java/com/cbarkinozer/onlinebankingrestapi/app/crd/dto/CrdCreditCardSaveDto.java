package com.cbarkinozer.onlinebankingrestapi.app.crd.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CrdCreditCardSaveDto {

    @NotNull
    private BigDecimal earning;
    private Integer cutOffDay;
}
