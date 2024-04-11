package com.replay.subscriptionservice.controller;

import com.replay.subscriptionservice.dto.SubscriptionRequest;
import com.replay.subscriptionservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createSubscription(@RequestBody SubscriptionRequest subscriptionRequest) {
        return subscriptionService.createSubscription(subscriptionRequest);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Boolean checkForSubscription(@RequestBody SubscriptionRequest subscriptionRequest ) {
        return subscriptionService.checkForSubscription(subscriptionRequest) != null;
    }
}
