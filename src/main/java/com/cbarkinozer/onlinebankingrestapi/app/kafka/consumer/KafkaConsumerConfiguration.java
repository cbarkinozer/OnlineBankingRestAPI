package com.cbarkinozer.onlinebankingrestapi.app.kafka.consumer;

import com.cbarkinozer.onlinebankingrestapi.app.kafka.dto.LogMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

    @Value("${cbarkinozer.kafka.adddress}")
    private String kafkaAddress;

    @Value("${cbarkinozer.kafka.group-id}")
    private String kafkaGroupId;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LogMessage> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, LogMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, LogMessage> consumerFactory(){

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, LogMessage.class);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config);
    }
}
