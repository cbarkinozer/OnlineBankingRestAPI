package com.cbarkinozer.onlinebankingrestapi.app.kafka.producer;

import com.cbarkinozer.onlinebankingrestapi.app.kafka.dto.LogMessage;
import com.cbarkinozer.onlinebankingrestapi.app.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka-messages")
@RequiredArgsConstructor
public class KafkaMessageController {

    private final LogService logService;

    @PostMapping
    public void sendMessage(@RequestBody LogMessage logMessage){

        System.out.println("starting to produce");

        logService.log(logMessage);

        System.out.println("message sent from producer");
    }
}
