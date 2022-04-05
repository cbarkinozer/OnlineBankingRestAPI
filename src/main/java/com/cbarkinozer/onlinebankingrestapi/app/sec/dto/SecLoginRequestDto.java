package com.cbarkinozer.onlinebankingrestapi.app.sec.dto;

import lombok.Data;

@Data
public class SecLoginRequestDto {

    private Long identityNo;
    private String password;
}
