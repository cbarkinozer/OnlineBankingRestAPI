package com.cbarkinozer.onlinebankingrestapi.app.kafka.consumer;

import com.cbarkinozer.onlinebankingrestapi.app.kafka.dto.LogMessage;
import com.cbarkinozer.onlinebankingrestapi.app.log.mapper.LogMapper;
import com.cbarkinozer.onlinebankingrestapi.app.log.entity.LogDetail;
import com.cbarkinozer.onlinebankingrestapi.app.log.service.entityservice.LogDetailEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerService {

    private final LogDetailEntityService logDetailEntityService;

    @KafkaListener(
            topics = "${cbarkinozer.kafka.topic}",
            groupId = "${cbarkinozer.kafka.group-id}"
    )
    public void listen(@Payload LogMessage logMessage){

        log.info("Message received by consumer... " + logMessage.getMessage());

        saveLogToDb(logMessage);
    }

    @Transactional
    public void saveLogToDb(LogMessage logMessage) {
        LogDetail logDetail = LogMapper.INSTANCE.convertToLogDetail(logMessage);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("**********************************");

        logDetail = logDetailEntityService.save(logDetail);

        System.out.println("end");
    }
}
