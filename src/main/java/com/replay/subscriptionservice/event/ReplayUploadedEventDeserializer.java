package com.replay.subscriptionservice.event;

import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
public class ReplayUploadedEventDeserializer implements Deserializer<ReplayUploadedEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ReplayUploadedEvent deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, ReplayUploadedEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing ReplayUploadedEvent", e);
        }
    }
}
