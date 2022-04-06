package com.vega.repositories;

import com.vega.entities.Contact;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.enterprise.context.ApplicationScoped;
import java.time.ZonedDateTime;
import java.util.UUID;

@ApplicationScoped
public class ContactRepository implements PanacheRepositoryBase<Contact, UUID> {

    private SessionFactory sessionFactory;

    // Может быть удален из-за существования метода persist, определенного в PanacheRepositoryBase
    // (created_at и modified_at автоматически выставляются hibernate'ом из-за аннотаций перед этими полями
    // в классах сущностей, id также автоматически генерируется)
    public Contact addContact(Contact contactToSave) {
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

    // Может быть удален из-за существования метода deleteById, определенного в PanacheRepositoryBase
    public Boolean deleteContact(UUID id) {
        Session session = sessionFactory.openSession();
        // session.beginTransaction();
        Contact contact = new Contact();
        contact.setId(id);
        session.delete(contact);
        session.getTransaction().commit();
        return true;
    }

    // Может быть удален из-за существования метода findById, определенного в PanacheRepositoryBase
    public Contact getContact(UUID id) {
        Session session = sessionFactory.openSession();
        return session.get(Contact.class, id);
    }

    public Contact findByIdAndUserId(UUID id, String userId) {
        return find("id = ?1 and user_id = ?2", id, userId).firstResult();
    }

    public Contact editContact(UUID id, Contact contactToSave) {
        Contact contact = findById(id);
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
        return contact;
    }
}
