package com.vacancinated.db.repositories;

import com.vacancinated.db.entity.Event;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@ApplicationScoped
public class EventRepository implements PanacheRepository<Event> {
    @Inject
    EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Event> findEventsWithCurrentNotification() {
        return (List<Event>) entityManager.createNativeQuery(
                "SELECT e.* " +
                        "FROM events e " +
                        "WHERE (begin_date - CAST(notify_for || ' minutes' AS INTERVAL)) = ?", Event.class)
                .setParameter(1, ZonedDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .getResultList();
    }
}
