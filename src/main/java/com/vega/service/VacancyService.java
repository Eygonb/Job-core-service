package com.vega.service;

import com.vega.entities.Vacancy;
import com.vega.repositories.VacancyRepository;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class VacancyService {
    @Inject
    VacancyRepository repository;

    public List<Vacancy> getAll(List<String> sortQuery, int pageIndex, int pageSize) {
        Page page = Page.of(pageIndex, pageSize);
        //  Sort sort = getSortFromQuery(sortQuery);
        return repository.findAll().page(page).list();
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

    public Vacancy add(Vacancy vacancyToSave) {
        repository.persist(vacancyToSave);
        return repository.findById(vacancyToSave.getId());
    }

    public Vacancy update(UUID id, Vacancy vacancy) {
        return repository.editVacancy(id, vacancy);
    }
}
