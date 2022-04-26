package com.vega.service;

import com.vega.entities.Status;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import com.vega.repositories.StatusRepository;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class StatusService {
    @Inject
    StatusRepository repository;

    public List<Status> getAll(List<Sorter> sorts, List<Filter> filters, int pageIndex, int pageSize, String userId) {
        Page page = Page.of(pageIndex, pageSize);
        return repository.findAll(sorts, filters, page, userId);
    }

    public Status get(Status.StatusKey id) {
        return repository.findById(id);
    }

    public Status getByIdAndUserId(Status.StatusKey id, String userId) {
        Status entity = repository.findById(id);
        if (entity != null && entity.getKey().getUserId().equals(userId)) {
            return entity;
        }
        return null;
    }

    public Boolean delete(Status.StatusKey key) {
        if (get(key) != null) {
            return repository.deleteById(key);
        }
        return false;
    }

    public Boolean deleteWithUserId(Status.StatusKey id, String userId) {
        if (getByIdAndUserId(id, userId) != null) {
            return repository.deleteById(id);
        }
        return false;
    }

    public Status add(String name, String userId) {
        Status status = new Status();
        Status.StatusKey key = new Status.StatusKey();
        key.setUserId(userId);
        key.setNameStatus(name);
        status.setKey(key);
        repository.persist(status);
        return repository.findById(status.getKey());
    }

    public Status update(Status.StatusKey id, Status status) {
        Status upStatus = repository.findById(id);
        upStatus.setKey(status.getKey());
        return upStatus;
    }

    public Long count(List<Filter> filters, String userId) {
        return repository.countStatus(filters, userId);
    }

}
