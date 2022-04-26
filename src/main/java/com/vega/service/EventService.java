package com.vega.service;

import com.vega.entities.Event;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
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

    public List<Event> getAll(List<Sorter> sorts, List<Filter> filters, int pageIndex, int pageSize, String userId) {
        Page page = Page.of(pageIndex, pageSize);
        return repository.findAll(sorts, filters, page, userId);
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

    public Event add(Event eventToSave, String userId) {
        eventToSave.setUserId(userId);
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
        return event;
    }

    public List<Event> getByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public Long count(List<Filter> filters, String userId) {
        return repository.countEvent(filters, userId);
    }
}
