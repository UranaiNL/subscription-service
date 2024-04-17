package com.replay.subscriptionservice.config;

import com.replay.subscriptionservice.event.ReplayUploadedEvent;
import com.replay.subscriptionservice.event.ReplayUploadedEventDeserializer;
import com.replay.subscriptionservice.service.SubscriptionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@Slf4j
@AllArgsConstructor
public class KafkaConsumerConfig {

    private final SubscriptionService subscriptionService;

    @Bean
    public ConsumerFactory<String, ReplayUploadedEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new ReplayUploadedEventDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReplayUploadedEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ReplayUploadedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @KafkaListener(topics = "subscriptionTopic")
    public void handleUpload(ReplayUploadedEvent replayUploadedEvent){
        log.info("Received Notification - {} uploaded by {}", replayUploadedEvent.getId(), replayUploadedEvent.getUploaderId());
        subscriptionService.notifySubscriptions(replayUploadedEvent);
    }
}
