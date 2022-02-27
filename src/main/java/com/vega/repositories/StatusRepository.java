package com.vega.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import com.vega.entities.Status;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.enterprise.context.ApplicationScoped;
import java.time.ZonedDateTime;
import java.util.UUID;

@ApplicationScoped
public class StatusRepository implements PanacheRepository<Status> {

    private SessionFactory sessionFactory;

    public Status addStatus(Status statusToSave)
    {
        Status status = new Status();
        Session session = sessionFactory.openSession();
        status.setCreatedAt(ZonedDateTime.now());
        status.setModifiedAt(ZonedDateTime.now());
        status.setKey(statusToSave.getKey());
        session.saveOrUpdate(status);
        session.getTransaction().commit();
        return status;

    }

    public Boolean deleteStatus(Status.StatusKey key)
    {
        Session session = sessionFactory.openSession();
        Status status = new Status();
        status.setKey(key);
        session.delete(status);
        session.getTransaction().commit();
        return true;
    }

    public Status getStatus(Status.StatusKey key)
    {
        Session session = sessionFactory.openSession();
        return session.get(Status.class,key);

    }

    public Status editStatus(Status.StatusKey key, Status statusToSave)
    {
        Session session = sessionFactory.openSession();
        Status status = session.load(Status.class,key);
        status.setModifiedAt(ZonedDateTime.now());
        status.setKey(statusToSave.getKey());
        session.saveOrUpdate(status);
        session.getTransaction().commit();
        return status;

    }


}
