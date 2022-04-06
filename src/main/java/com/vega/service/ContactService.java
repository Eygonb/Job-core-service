package com.vega.service;

import com.vega.entities.Contact;
import com.vega.entities.Vacancy;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import com.vega.repositories.ContactRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.security.identity.SecurityIdentity;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@ApplicationScoped
public class ContactService {
    @Inject
    ContactRepository repository;

    public List<Contact> getAll(List<Sorter> sorts, List<Filter> filters, int pageIndex, int pageSize) {
        Page page = Page.of(pageIndex, pageSize);
        List<Contact> contacts = repository.findAll(sorts,filters ,page);
        Contact contact = new Contact();
        contact.setUserId(SecurityIdentity.USER_ATTRIBUTE);
        Predicate<Contact> predicateOne = c -> c.getUserId().equals(contact.getUserId());
        return (List<Contact>) contacts.stream().filter(predicateOne);
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
        contactToSave.setUserId(SecurityIdentity.USER_ATTRIBUTE);
        repository.persist(contactToSave);
        return repository.findById(contactToSave.getId());
    }

    public Contact update(UUID id, Contact contact) {
        return repository.editContact(id, contact);
    }
}
