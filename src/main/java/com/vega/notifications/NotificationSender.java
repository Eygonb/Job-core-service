package com.vega.notifications;


import com.vega.entities.Event;
import com.vega.notifications.kafka.KafkaNotificationSender;
import com.vega.notifications.model.Notification;
import com.vega.repositories.EventRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class NotificationSender {
    @Inject
    EventRepository eventRepository;
    @Inject
    KafkaNotificationSender kafkaNotificationSender;

    public void sendCurrentNotifications() {
        List<Event> events = eventRepository.findEventsWithCurrentNotification();

        for (Event event : events) {
            Notification notification = new Notification(event);

            kafkaNotificationSender.sendNotification(notification);
        }
    }
}
