package com.cbarkinozer.onlinebankingrestapi.app.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogMessage implements Serializable {

    private Long id ;
    private String message;
    private String description;
    private LocalDateTime dateTime;
}
