package com.vega.service;

import com.vega.entities.Contact;
import com.vega.repositories.ContactRepository;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ContactService {
    @Inject
    ContactRepository repository;

    public List<Contact> getAll(List<String> sortQuery, int pageIndex, int pageSize) {
        Page page = Page.of(pageIndex, pageSize);
        //  Sort sort = getSortFromQuery(sortQuery);
        return repository.findAll().page(page).list();
    }

    public Contact get(UUID id) {
        return repository.findById(id);
    }

    public Contact getByIdAndUserId(UUID id, String userId) {
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

    public Contact add(Contact contactToSave) {
        repository.persist(contactToSave);
        return repository.findById(contactToSave.getId());
    }

    public Contact update(UUID id, Contact contact) {
        return repository.editContact(id, contact);
    }
}
