package com.vacancinated.notifications.kafka;

import com.vacancinated.notifications.model.Notification;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class KafkaNotificationSender {
    @Inject
    @Channel("notifications")
    Emitter<Notification> notificationEmitter;

    public void sendNotification(Notification notification) {
        notificationEmitter.send(notification);
    }
}
