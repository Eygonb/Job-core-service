package com.vega.service;

import com.vega.entities.Event;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import com.vega.repositories.EventRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.security.identity.SecurityIdentity;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@ApplicationScoped
public class EventService {
    @Inject
    EventRepository repository;

    public List<Event> getAll(List<Sorter> sorts, List<Filter> filters, int pageIndex, int pageSize) {
        Page page = Page.of(pageIndex, pageSize);
        List<Event> events = repository.findAll(sorts,filters ,page);
        Event event = new Event();
        event.setUserId(SecurityIdentity.USER_ATTRIBUTE);
        Predicate<Event> predicateOne = ev -> ev.getUserId().equals(event.getUserId());
        return (List<Event>) events.stream().filter(predicateOne);
    }

    public Event get(UUID id) {
        return repository.findById(id);
    }

    public Event getByIdAndUserId(UUID id, String userId) {
        return repository.findByIdAndUserId(id, userId);
    }

    public Boolean delete(UUID id) {
        return repository.deleteById(id);
    }

    public Boolean deleteWithUserId(UUID id, String userId) {
        if (repository.findByIdAndUserId(id, userId) != null) {
            return repository.deleteById(id);
        }
        return false;
    }

    public Event add(Event eventToSave) {
        eventToSave.setUserId(SecurityIdentity.USER_ATTRIBUTE);
        repository.persist(eventToSave);
        return repository.findById(eventToSave.getId());
    }

    public Event update(UUID id, Event eventToSave) {
        Event event = repository.findById(id);
        event.setName(eventToSave.getName());
        event.setBeginDate(eventToSave.getBeginDate());
        event.setEndDate(eventToSave.getEndDate());
        event.setIsCompleted(eventToSave.getIsCompleted());
        event.setVacancyId(eventToSave.getVacancyId());
        event.setNotifyFor(eventToSave.getNotifyFor());
        repository.persist(event);
        return  event;
    }


    public List<Event> getByUserId(String userId) {
        return repository.findByUserId(userId);
    }

}
