package com.vega.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import com.vega.entities.Event;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.ZonedDateTime;
import java.util.UUID;

public class EventRepository implements PanacheRepository<Event> {

    private SessionFactory sessionFactory;

    public Event addEvent(Event eventToSave)
    {
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

    public Boolean deleteEvent(UUID id)
    {
        Session session = sessionFactory.openSession();
        Event event = new Event();
        event.setId(id);
        session.delete(event);
        session.getTransaction().commit();
        return true;
    }

    public Event getEvent(UUID id)
    {
        Session session = sessionFactory.openSession();
        return session.get(Event.class,id);

    }

    public Event editEvent(UUID id, Event eventToSave)
    {
        Session session = sessionFactory.openSession();
        Event event = session.load(Event.class,id);
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
}
