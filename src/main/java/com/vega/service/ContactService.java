package com.vega.service;

import com.vega.entities.Contact;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import com.vega.repositories.ContactRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.security.identity.SecurityIdentity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ContactService {
    @Inject
    ContactRepository repository;

    public List<Contact> getAll(List<Sorter> sorts, List<Filter> filters, int pageIndex, int pageSize, String userId) {
        Page page = Page.of(pageIndex, pageSize);
        return repository.findAll(sorts, filters, page, userId);
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

    public Contact add(Contact contactToSave, String userId) {
        contactToSave.setUserId(userId);
        repository.persist(contactToSave);
        return repository.findById(contactToSave.getId());
    }

    public Contact update(UUID id, Contact contactToSave) {
        Contact contact = repository.findById(id);
        contact.setFirstName(contactToSave.getFirstName());
        contact.setLastName(contactToSave.getLastName());
        contact.setCity(contactToSave.getCity());
        contact.setCompany(contactToSave.getCompany());
        contact.setSkype(contactToSave.getSkype());
        contact.setTelegram(contactToSave.getTelegram());
        contact.setTelephone(contactToSave.getTelephone());
        contact.setNotes(contactToSave.getNotes());
        contact.setMail(contactToSave.getMail());
        contact.setPosition(contactToSave.getPosition());
        contact.setVk(contactToSave.getVk());
        contact.setVacancyId(contactToSave.getVacancyId());
        repository.persist(contact);
        return contact;
    }

    public Long count(List<Filter> filters, String userId) {
        return repository.countContact(filters, userId);
    }

}
