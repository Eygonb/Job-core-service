package com.vega.service;

import com.vega.entities.Status;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import com.vega.repositories.StatusRepository;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
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

    public Status add(Status.StatusKey key) {
        Status status = new Status();
        status.setKey(key);
        status.setOrderNum(repository.getMaxOrderNum() + 1);
        repository.persist(status);
        return repository.findById(status.getKey());
    }

    public List<Status> updateAll(List<Status> statuses, String userId) {
        List<Status> changedStatuses = new ArrayList<>();
        for (int i = 0; i < statuses.size(); i++) {
            if (statuses.get(i).getKey().getUserId().equals(userId)) {
                statuses.get(i).setOrderNum(i);
                changedStatuses.add(update(statuses.get(i).getKey(), statuses.get(i)));
            }
        }
        return changedStatuses;
    }

    public Status update(Status.StatusKey key, Status status) {
        Status upStatus = repository.findById(key);
        upStatus.getKey().setUserId(status.getKey().getUserId());
        upStatus.getKey().setNameStatus(status.getKey().getNameStatus());
        if (status.getOrderNum() != null) {
            upStatus.setOrderNum(status.getOrderNum());
        }
        return upStatus;
    }

    public Long count(List<Filter> filters, String userId) {
        return repository.countStatus(filters, userId);
    }
}
