package com.vega.repositories;

import com.vega.entities.Status;
import com.vega.entities.Vacancy;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.security.identity.SecurityIdentity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Where;
import org.hibernate.sql.Select;

import javax.enterprise.context.ApplicationScoped;
import javax.management.Query;
import javax.persistence.PersistenceException;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

@ApplicationScoped
public class VacancyRepository implements PanacheRepositoryBase<Vacancy,UUID> {

    public List<Vacancy> findAll(List<Sorter> sorts, List<Filter> filters, Page page)
    {
      //  List<Vacancy> vacancies = findAll().list();
       // PanacheQuery<Vacancy> queryVacancy = find("property", filters.get(0).getProperty(),
        //        "operator",filters.get(0).getFilterOperator(),"value", filters.get(0).getValue());
        PanacheQuery<Vacancy> queryVacancy = find("select * from vacancies v " +
                "where v."+ filters.get(0).getProperty() + " " + filters.get(0).getFilterOperator() + " " +
                filters.get(0).getValue() + " and v." + filters.get(1).getProperty() + " " + filters.get(1).getFilterOperator() +
                " " + filters.get(1).getValue() + " order by " + sorts.get(0).getProperty() + " " + sorts.get(0).getSortDirection() +
                "," + sorts.get(1).getProperty() + " "+ sorts.get(1).getSortDirection()).page(page);
        return queryVacancy.list();

    }


}
