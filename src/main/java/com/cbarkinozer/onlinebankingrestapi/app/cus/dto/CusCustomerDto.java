package com.cbarkinozer.onlinebankingrestapi.app.cus.dto;

import lombok.Data;

@Data
public class CusCustomerDto {

    Long id;
    private String name;
    private Long identityNo;
}
