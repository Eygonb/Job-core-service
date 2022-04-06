package com.vega.repositories;

import com.vega.entities.Event;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.enterprise.context.ApplicationScoped;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EventRepository implements PanacheRepositoryBase<Event, UUID> {

    private SessionFactory sessionFactory;

    // Может быть удален из-за существования метода persist, определенного в PanacheRepositoryBase
    // (created_at и modified_at автоматически выставляются hibernate'ом из-за аннотаций перед этими полями
    // в классах сущностей, id также автоматически генерируется)
    public Event addEvent(Event eventToSave) {
        Event event = new Event();
        Session session = sessionFactory.openSession();
        event.setVacancyId(eventToSave.getVacancyId());
        event.setCreatedAt(ZonedDateTime.now());
        event.setUserId((eventToSave.getUserId()));
        event.setModifiedAt(ZonedDateTime.now());
        event.setId(UUID.randomUUID());
        event.setName(eventToSave.getName());
        event.setNotes(eventToSave.getNotes());
        event.setBeginDate(eventToSave.getBeginDate());
        event.setEndDate(eventToSave.getEndDate());
        event.setIsCompleted(eventToSave.getIsCompleted());
        session.saveOrUpdate(event);
        session.getTransaction().commit();
        return event;
    }


    // Может быть удален из-за существования метода deleteById, определенного в PanacheRepositoryBase
    public Boolean deleteEvent(UUID id) {
        Session session = sessionFactory.openSession();
        Event event = new Event();
        event.setId(id);
        session.delete(event);
        session.getTransaction().commit();
        return true;
    }

    // Может быть удален из-за существования метода findById, определенного в PanacheRepositoryBase
    public Event getEvent(UUID id) {
        Session session = sessionFactory.openSession();
        return session.get(Event.class, id);
    }

    public Event findByIdAndUserId(UUID id, String userId) {
        return find("id = ?1 and user_id = ?2", id, userId).firstResult();
    }

    public Event editEvent(UUID id, Event eventToSave) {
        Session session = sessionFactory.openSession();
        Event event = session.load(Event.class, id);
        event.setVacancyId(eventToSave.getVacancyId());
        event.setModifiedAt(ZonedDateTime.now());
        event.setName(eventToSave.getName());
        event.setNotes(eventToSave.getNotes());
        event.setBeginDate(eventToSave.getBeginDate());
        event.setEndDate(eventToSave.getEndDate());
        event.setIsCompleted(eventToSave.getIsCompleted());
        session.saveOrUpdate(event);
        session.getTransaction().commit();
        return event;
    }

    public List<Event> findByUserId(String userId) {
        return list("user_id", userId);
    }
}
