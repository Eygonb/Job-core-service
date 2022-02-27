package com.vega.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import com.vega.entities.Contact;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.enterprise.context.ApplicationScoped;
import java.time.ZonedDateTime;
import java.util.UUID;

@ApplicationScoped
public class ContactRepository implements PanacheRepository<Contact> {

    private SessionFactory sessionFactory;

    public Contact addContact(Contact contactToSave)
    {
        Contact contact = new Contact();
        Session session = sessionFactory.openSession();
       // session.beginTransaction();
        contact.setVacancyId(contactToSave.getVacancyId());
        contact.setCompany(contactToSave.getCompany());
        contact.setNotes(contactToSave.getNotes());
        contact.setFirstName(contactToSave.getFirstName());
        contact.setLastName(contactToSave.getLastName());
        contact.setCreatedAt(ZonedDateTime.now());
        contact.setModifiedAt(ZonedDateTime.now());
        contact.setId(UUID.randomUUID());
        contact.setCity(contactToSave.getCity());
        contact.setPosition(contactToSave.getPosition());
        contact.setMail(contactToSave.getMail());
        contact.setTelegram(contactToSave.getTelegram());
        contact.setVk(contactToSave.getVk());
        contact.setSkype(contactToSave.getSkype());
        contact.setTelephone(contactToSave.getTelephone());
        session.saveOrUpdate(contact);
        session.getTransaction().commit();
        return contact;

    }

    public Boolean deleteContact(UUID id)
    {
        Session session = sessionFactory.openSession();
       // session.beginTransaction();
        Contact contact = new Contact();
        contact.setId(id);
        session.delete(contact);
        session.getTransaction().commit();
        return true;
    }

    public Contact getContact(UUID id)
    {
        Session session = sessionFactory.openSession();
        return session.get(Contact.class,id);

    }

    public Contact editContact(UUID id, Contact contactToSave)
    {
        Session session = sessionFactory.openSession();
        Contact contact = session.load(Contact.class,id);
      //  session.beginTransaction();
        contact.setVacancyId(contactToSave.getVacancyId());
        contact.setUserId(contactToSave.getUserId());
        contact.setCompany(contactToSave.getCompany());
        contact.setNotes(contactToSave.getNotes());
        contact.setFirstName(contactToSave.getFirstName());
        contact.setLastName(contactToSave.getLastName());
        contact.setModifiedAt(ZonedDateTime.now());
        contact.setCity(contactToSave.getCity());
        contact.setPosition(contactToSave.getPosition());
        contact.setMail(contactToSave.getMail());
        contact.setTelegram(contactToSave.getTelegram());
        contact.setVk(contactToSave.getVk());
        contact.setSkype(contactToSave.getSkype());
        contact.setTelephone(contactToSave.getTelephone());
        session.saveOrUpdate(contact);
        session.getTransaction().commit();
        return contact;

    }
}
