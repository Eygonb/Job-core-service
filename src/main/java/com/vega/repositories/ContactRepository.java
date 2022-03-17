package com.vega.repositories;

import com.vega.entities.Vacancy;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import com.vega.entities.Contact;
import io.quarkus.panache.common.Page;
import io.quarkus.security.identity.SecurityIdentity;


import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;


@ApplicationScoped
public class ContactRepository implements PanacheRepositoryBase<Contact, UUID> {
    public List<Contact> findAll(List<Sorter> sorts, List<Filter> filters, Page page)
    {
       /* Contact contact = new Contact();
        contact.setUserId(SecurityIdentity.USER_ATTRIBUTE);
        Predicate<Vacancy> predicateOne = vc -> vc.getUserId().equals(contact.getUserId());*/
        PanacheQuery<Contact> queryContact = find("select * from contacts c " +
                "where c."+ filters.get(0).getProperty() + " " + filters.get(0).getFilterOperator() + " " +
                filters.get(0).getValue() + " and c." + filters.get(1).getProperty() + " " + filters.get(1).getFilterOperator() +
                " " + filters.get(1).getValue() + " order by " + sorts.get(0).getProperty() + " " + sorts.get(0).getSortDirection() +
                "," + sorts.get(1).getProperty() + " "+ sorts.get(1).getSortDirection()).page(page);
        return queryContact.list();

    }


}
