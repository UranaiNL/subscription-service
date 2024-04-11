package com.replay.subscriptionservice.repository;

import com.replay.subscriptionservice.model.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    Optional<Subscription> findBySubscriberIdAndSubscribedToAndType(String subscriberId, String subscribedTo, String type);
    List<Subscription> findAllBySubscribedToAndType(String subscribedTo, String type);
}
