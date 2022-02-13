package com.vacancinated.notifications.kafka;

import com.vacancinated.notifications.model.Notification;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class KafkaNotificationProducer {

    //@Outgoing("notifications")
    //public Notification sendNotification(Notification notification) {
    //    return notification;
    //}
}
