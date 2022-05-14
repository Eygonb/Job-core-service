package com.vega.service;

import com.vega.entities.Vacancy;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import io.quarkus.panache.common.Page;
import com.vega.repositories.VacancyRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class VacancyService {
    @Inject
    VacancyRepository repository;

    public List<Vacancy> getAll(List<Sorter> sorts, List<Filter> filters, int pageIndex, int pageSize, String userId) {
        Page page = Page.of(pageIndex, pageSize);
        return repository.findAll(sorts, filters, page, userId);
    }


    public Vacancy get(UUID id) {
        return repository.findById(id);
    }

    public Vacancy getByIdAndUserId(UUID id, String userId) {
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

    public Vacancy add(Vacancy vacancyToSave, String userId) {
        vacancyToSave.setUserId(userId);
        repository.persist(vacancyToSave);
        return repository.findById(vacancyToSave.getId());
    }


    public Vacancy update(UUID id, Vacancy vacancy) {
        Vacancy upVacancy = repository.findById(id);
        upVacancy.setNameVacancy(vacancy.getNameVacancy());
        upVacancy.setStatusId(vacancy.getStatusId());
        upVacancy.setLocationLatitude(vacancy.getLocationLatitude());
        upVacancy.setLocationLongitude(vacancy.getLocationLongitude());
        upVacancy.setCompany(vacancy.getCompany());
        upVacancy.setSalary(vacancy.getSalary());
        upVacancy.setNotes(vacancy.getNotes());
        upVacancy.setContacts(vacancy.getContacts());
        upVacancy.setEvents(vacancy.getEvents());
        return upVacancy;
    }

    public Long count(List<Filter> filters, String userId) {
        return repository.countVacancy(filters, userId);
    }
}
