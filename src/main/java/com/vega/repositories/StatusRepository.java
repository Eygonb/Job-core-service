package com.vega.repositories;

import com.vega.entities.Vacancy;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import com.vega.entities.Status;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class StatusRepository implements PanacheRepositoryBase<Status, Status.StatusKey> {

    public List<Status> findAll(List<Sorter> sorts, List<Filter> filters, Page page)
    {
        PanacheQuery<Status> queryStatus = find("select * from statuses s " +
                "where s."+ filters.get(0).getProperty() + " " + filters.get(0).getFilterOperator() + " " +
                filters.get(0).getValue() + " and s." + filters.get(1).getProperty() + " " + filters.get(1).getFilterOperator() +
                " " + filters.get(1).getValue() + " order by " + sorts.get(0).getProperty() + " " + sorts.get(0).getSortDirection() +
                "," + sorts.get(1).getProperty() + " "+ sorts.get(1).getSortDirection()).page(page);
        return queryStatus.list();

    }


}
