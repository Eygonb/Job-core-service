package com.vega.repositories;

import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import com.vega.entities.Contact;
import io.quarkus.panache.common.Page;


import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;



@ApplicationScoped
public class ContactRepository implements PanacheRepositoryBase<Contact, UUID> {

    public List<Contact> findAll(List<Sorter> sorts, List<Filter> filters, Page page) {
       /* Contact contact = new Contact();
        contact.setUserId(SecurityIdentity.USER_ATTRIBUTE);
        Predicate<Vacancy> predicateOne = vc -> vc.getUserId().equals(contact.getUserId());*/
        String allFilters = "";
        String allSorts = "";
        for (int i = 0; i < filters.size(); i++) {
            allFilters += " c." + filters.get(i).getProperty() + " " + filters.get(i).getFilterOperator() +
                    " '" + filters.get(i).getValue() + "'";
            if (i + 1 < filters.size())
                allFilters += " and";
            else
                allFilters += " ";
        }
        for (int j = 0; j < sorts.size(); j++) {
            allSorts += " c." + sorts.get(j).getProperty() + " " + sorts.get(0).getSortDirection();
            if (j + 1 < sorts.size())
                allSorts += ",";
        }
        PanacheQuery<Contact> queryContact = find("select * from contacts c " +
                "where" + allFilters + "order by" + allSorts).page(page);
        return queryContact.list();
    }

    public Contact findByIdAndUserId(UUID id, String userId) {
        return find("id = ?1 and user_id = ?2", id, userId).firstResult();
    }


}
