package com.vega.repositories;

import com.vega.entities.Contact;
import com.vega.entities.Vacancy;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import com.vega.entities.Event;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.security.identity.SecurityIdentity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.PersistenceException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.persist;

public class EventRepository implements PanacheRepositoryBase<Event,UUID> {

    public List<Event> findAll(List<Sorter> sorts, List<Filter> filters, Page page)
    {
        PanacheQuery<Event> queryEvent = find("select * from events ev " +
                "where ev."+ filters.get(0).getProperty() + " " + filters.get(0).getFilterOperator() + " " +
                filters.get(0).getValue() + " and ev." + filters.get(1).getProperty() + " " + filters.get(1).getFilterOperator() +
                " " + filters.get(1).getValue() + " order by " + sorts.get(0).getProperty() + " " + sorts.get(0).getSortDirection() +
                "," + sorts.get(1).getProperty() + " "+ sorts.get(1).getSortDirection()).page(page);
        return queryEvent.list();

    }
}
