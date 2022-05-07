package com.cbarkinozer.onlinebankingrestapi.app.loa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoaCalculateLateFeeDto {

    private BigDecimal totalLoan;
    private Integer lateDayCount;
}
