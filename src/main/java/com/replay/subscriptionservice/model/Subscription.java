package com.replay.subscriptionservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "subscription")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Subscription {
    @Id
    private String id;
    private String subscriberId;
    private String subscribedTo;
    private String type;
}
