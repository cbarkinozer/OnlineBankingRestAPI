package com.cbarkinozer.onlinebankingrestapi.app.loa.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LoaCalculateLateFeeResponseDto {

    private BigDecimal totalLateFee;
    private BigDecimal lateFeeRate;
    private BigDecimal lateInterestTax;
    private Long lateDayCount;


}
