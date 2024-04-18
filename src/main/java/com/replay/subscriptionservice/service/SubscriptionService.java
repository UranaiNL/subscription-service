package com.replay.subscriptionservice.service;

import com.replay.subscriptionservice.config.RabbitMQConfig;
import com.replay.subscriptionservice.dto.SubscriptionRequest;
import com.replay.subscriptionservice.event.ReplayUploadedEvent;
import com.replay.subscriptionservice.model.Subscription;
import com.replay.subscriptionservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public String createSubscription(SubscriptionRequest subscriptionRequest) {
        Subscription subscription = Subscription.builder()
                        .subscriberId(subscriptionRequest.getSubscriberId())
                        .type(subscriptionRequest.getType())
                        .subscribedTo(subscriptionRequest.getSubscribedTo())
                        .build();
        subscriptionRepository.save(subscription);
        log.info("Replay {} is saved", subscription.getId());
        return subscription.getId();
    }

    public Subscription checkForSubscription(SubscriptionRequest subscriptionRequest) {
        Optional<Subscription> subscription = subscriptionRepository.findBySubscriberIdAndSubscribedToAndType(subscriptionRequest.getSubscriberId(),
                subscriptionRequest.getSubscribedTo(), subscriptionRequest.getType());
        return subscription.orElse(null);
    }

    public List<Subscription> findAllSubscriptonsBySubscribedToAndType(SubscriptionRequest subscriptionRequest){
        return subscriptionRepository.findAllBySubscribedToAndType(subscriptionRequest.getSubscribedTo(), subscriptionRequest.getType());
    }

    @RabbitListener(queues = RabbitMQConfig.REPLAY_QUEUE)
    public void notifySubscriptions(ReplayUploadedEvent replayUploadedEvent){
        log.info("Notifying subscriptions that id {} has uploaded", replayUploadedEvent.getUploaderId());
//        List<String> subscriptionTypes = new ArrayList<>(List.of("uploader", "character", "player"));
//        List<Subscription> subscriptionsToNotify = new ArrayList<>();
//        for(String type : subscriptionTypes){
//            SubscriptionRequest request = mapToSubscriptionRequest(replayUploadedEvent, type);
//            subscriptionsToNotify.addAll(findAllSubscriptonsBySubscribedToAndType(request));
//        }
//        for (Subscription subscription : subscriptionsToNotify){
//            return;
//        }
    }

    private SubscriptionRequest mapToSubscriptionRequest(ReplayUploadedEvent replayUploadedEvent, String type) {
        return SubscriptionRequest.builder()
                .subscribedTo(replayUploadedEvent.getUploaderId())
                .type(type)
                .subscriberId("0")
                .build();
    }
}
