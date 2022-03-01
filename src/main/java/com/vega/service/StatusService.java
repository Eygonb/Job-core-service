package com.vega.service;

import com.vega.entities.Status;
import com.vega.repositories.StatusRepository;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class StatusService {
    @Inject
    StatusRepository repository;

    public List<Status> getAll(List<String> sortQuery, int pageIndex, int pageSize) {
        Page page = Page.of(pageIndex, pageSize);
        //  Sort sort = getSortFromQuery(sortQuery);
        return repository.findAll().page(page).list();
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
