package com.vega.notifications;

import io.quarkus.scheduler.Scheduled;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class NotificationScheduler {
    @Inject
    NotificationSender sender;

    @Scheduled(every = "{scheduled.time}")
    void send() {
        sender.sendCurrentNotifications();
    }
}
