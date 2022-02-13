package com.vacancinated.notifications;

import com.vacancinated.db.entity.Event;
import com.vacancinated.db.repositories.EventRepository;
import com.vacancinated.notifications.kafka.KafkaNotificationSender;
import com.vacancinated.notifications.model.Notification;

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
