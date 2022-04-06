package com.vega.service;

import com.vega.entities.Event;
import com.vega.repositories.EventRepository;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EventService {
    @Inject
    EventRepository repository;

    public List<Event> getAll(List<String> sortQuery, int pageIndex, int pageSize) {
        Page page = Page.of(pageIndex, pageSize);
        //  Sort sort = getSortFromQuery(sortQuery);
        return repository.findAll().page(page).list();
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
        repository.persist(eventToSave);
        return repository.findById(eventToSave.getId());
    }

    public Event update(UUID id, Event event) {
        return repository.editEvent(id, event);
    }

    public List<Event> getByUserId(String userId) {
        return repository.findByUserId(userId);
    }
}
