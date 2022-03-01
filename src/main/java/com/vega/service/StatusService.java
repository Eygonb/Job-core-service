package com.vega.service;

import com.vega.entities.Status;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import com.vega.repositories.StatusRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.security.identity.SecurityIdentity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.function.Predicate;

@ApplicationScoped
public class StatusService {

    @Inject
    StatusRepository repository;
    Status.StatusKey key;

    public List<Status> getAll(List<Sorter> sorts, List<Filter> filters, int pageIndex, int pageSize) {
        Page page = Page.of(pageIndex, pageSize);
        List<Status> statuses = repository.findAll(sorts,filters ,page);
        key.setUserId(SecurityIdentity.USER_ATTRIBUTE);
        Predicate<Status> predicateOne = s -> s.getKey().getUserId().equals(key.getUserId());
        return (List<Status>) statuses.stream().filter(predicateOne);
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

    public Boolean delete(Status.StatusKey id) {
        return repository.deleteById(id);
    }

    public Boolean deleteWithUserId(Status.StatusKey id, String userId) {
        if (getByIdAndUserId(id, userId) != null) {
            return repository.deleteById(id);
        }
        return false;
    }

    public Status add(Status statusToSave) {
        repository.persist(statusToSave);
        return repository.findById(statusToSave.getKey());
    }

    public Status update(Status.StatusKey id, Status status) {
        return repository.editStatus(id, status);
    }
}
