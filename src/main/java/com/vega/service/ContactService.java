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


    public Boolean delete(UUID id) {
        return repository.deleteById(id);
    }


    public Contact add(Contact contactToSave) {
        contactToSave.setUserId(SecurityIdentity.USER_ATTRIBUTE);
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
        return  contact;
    }
}

