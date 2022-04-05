package com.cbarkinozer.onlinebankingrestapi.app.acc.dto;

import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccAccountActivityType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccMoneyActivityDto {

    private Long accountId;
    private BigDecimal amount;
    private AccAccountActivityType activityType;

}
