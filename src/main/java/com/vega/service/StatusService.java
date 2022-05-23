package com.vega.service;

import com.vega.entities.Status;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import com.vega.repositories.StatusRepository;
import com.vega.repositories.VacancyRepository;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class StatusService {
    @Inject
    StatusRepository repository;
    @Inject
    VacancyRepository vacancyRepository;

    public List<Status> getAll(List<Sorter> sorts, List<Filter> filters, int pageIndex, int pageSize, String userId) {
        Page page = Page.of(pageIndex, pageSize);
        return repository.findAll(sorts, filters, page, userId);
    }

    public Status get(UUID id) {
        return repository.findById(id);
    }

    public Status getByIdAndUserId(UUID id, String userId) {
        return repository.findByIdAndUserId(id, userId);
    }

    public Boolean deleteWithUserId(UUID id, String userId) {
        if (getByIdAndUserId(id, userId) != null) {
            vacancyRepository.deleteByStatusId(id);
            return repository.deleteById(id);
        }
        return false;
    }

    public Status add(Status status, String userId) {
        status.setUserId(userId);
        status.setOrderNum(repository.getMaxOrderNum() + 1);
        repository.persist(status);
        return repository.findById(status.getId());
    }

    public List<Status> updateAll(List<Status> statuses, String userId) {
        List<Status> changedStatuses = new ArrayList<>();
        for (int i = 0; i < statuses.size(); i++) {
            if (statuses.get(i).getUserId().equals(userId)) {
                statuses.get(i).setOrderNum(i);
                changedStatuses.add(update(statuses.get(i).getId(), statuses.get(i)));
            }
        }
        return changedStatuses;
    }

    public Status update(UUID id, Status status) {
        Status upStatus = repository.findById(id);
        upStatus.setUserId(status.getUserId());
        upStatus.setNameStatus(status.getNameStatus());
        if (status.getOrderNum() != null) {
            upStatus.setOrderNum(status.getOrderNum());
        }
        return upStatus;
    }

    public Long count(List<Filter> filters, String userId) {
        return repository.countStatus(filters, userId);
    }
}
