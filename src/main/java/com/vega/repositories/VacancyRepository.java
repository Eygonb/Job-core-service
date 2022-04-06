package com.vega.repositories;

import com.vega.entities.Vacancy;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.enterprise.context.ApplicationScoped;
import java.time.ZonedDateTime;
import java.util.UUID;

@ApplicationScoped
public class VacancyRepository implements PanacheRepositoryBase<Vacancy, UUID> {

    private SessionFactory sessionFactory;

    // Может быть удален из-за существования метода persist, определенного в PanacheRepositoryBase
    // (created_at и modified_at автоматически выставляются hibernate'ом из-за аннотаций перед этими полями
    // в классах сущностей, id также автоматически генерируется)
    public Vacancy addVacancy(Vacancy vacancyToSave) {
        Vacancy vacancy = new Vacancy();
        Session session = sessionFactory.openSession();
        // session.beginTransaction();
        vacancy.setNameVacancy(vacancyToSave.getNameVacancy());
        vacancy.setCompany(vacancyToSave.getCompany());
        vacancy.setUserId(vacancyToSave.getUserId());
        vacancy.setLocationLatitude(vacancyToSave.getLocationLatitude());
        vacancy.setLocationLongitude(vacancyToSave.getLocationLongitude());
        vacancy.setNotes(vacancyToSave.getNotes());
        vacancy.setSalary(vacancyToSave.getSalary());
        vacancy.setStatusName(vacancyToSave.getStatusName());
        vacancy.setCreatedAt(ZonedDateTime.now());
        vacancy.setModifiedAt(ZonedDateTime.now());
        vacancy.setId(UUID.randomUUID());
        session.saveOrUpdate(vacancy);
        session.getTransaction().commit();
        return vacancy;
    }

    // Может быть удален из-за существования метода deleteById, определенного в PanacheRepositoryBase
    public Boolean deleteVacancy(UUID id) {
        Session session = sessionFactory.openSession();
        // session.beginTransaction();
        Vacancy vacancy = new Vacancy();
        vacancy.setId(id);
        session.delete(vacancy);
        session.getTransaction().commit();
        return true;
    }

    // Может быть удален из-за существования метода findById, определенного в PanacheRepositoryBase
    public Vacancy getVacancy(UUID id) {
        Session session = sessionFactory.openSession();
        return session.get(Vacancy.class, id);
        // return (Vacancy)session.createQuery("Select v From Vacancy v Where id == v.getId().toString()");
    }

    public Vacancy findByIdAndUserId(UUID id, String userId) {
        return find("id = ?1 and user_id = ?2", id, userId).firstResult();
    }

    public Vacancy editVacancy(UUID id, Vacancy vacancyToSave) {
        Session session = sessionFactory.openSession();
        Vacancy vacancy = session.load(Vacancy.class, id);
        //session.beginTransaction();
        vacancy.setNameVacancy(vacancyToSave.getNameVacancy());
        vacancy.setCompany(vacancyToSave.getCompany());
        vacancy.setLocationLatitude(vacancyToSave.getLocationLatitude());
        vacancy.setLocationLongitude(vacancyToSave.getLocationLongitude());
        vacancy.setNotes(vacancyToSave.getNotes());
        vacancy.setSalary(vacancyToSave.getSalary());
        vacancy.setStatusName(vacancyToSave.getStatusName());
        vacancy.setModifiedAt(ZonedDateTime.now());
        session.saveOrUpdate(vacancy);
        session.getTransaction().commit();
        return vacancy;
    }
}
