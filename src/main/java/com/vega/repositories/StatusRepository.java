package com.vega.repositories;

import com.vega.entities.Status;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.enterprise.context.ApplicationScoped;
import java.time.ZonedDateTime;

@ApplicationScoped
public class StatusRepository implements PanacheRepositoryBase<Status, Status.StatusKey> {

    private SessionFactory sessionFactory;

    // Может быть удален из-за существования метода persist, определенного в PanacheRepositoryBase
    // (created_at и modified_at автоматически выставляются hibernate'ом из-за аннотаций перед этими полями
    // в классах сущностей, id также автоматически генерируется)
    public Status addStatus(Status statusToSave) {
        Status status = new Status();
        Session session = sessionFactory.openSession();
        status.setCreatedAt(ZonedDateTime.now());
        status.setModifiedAt(ZonedDateTime.now());
        status.setKey(statusToSave.getKey());
        session.saveOrUpdate(status);
        session.getTransaction().commit();
        return status;

    }

    // Может быть удален из-за существования метода deleteById, определенного в PanacheRepositoryBase
    public Boolean deleteStatus(Status.StatusKey key) {
        Session session = sessionFactory.openSession();
        Status status = new Status();
        status.setKey(key);
        session.delete(status);
        session.getTransaction().commit();
        return true;
    }

    // Может быть удален из-за существования метода findById, определенного в PanacheRepositoryBase
    public Status getStatus(Status.StatusKey key) {
        Session session = sessionFactory.openSession();
        return session.get(Status.class, key);

    }

    public Status editStatus(Status.StatusKey key, Status statusToSave) {
        Session session = sessionFactory.openSession();
        Status status = session.load(Status.class, key);
        status.setModifiedAt(ZonedDateTime.now());
        status.setKey(statusToSave.getKey());
        session.saveOrUpdate(status);
        session.getTransaction().commit();
        return status;

    }


}
