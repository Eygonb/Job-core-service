package com.vega.repositories;

import com.vega.entities.Contact;
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
       /* String allFilters ="";
        String allSorts ="";
        for(int i=0;i<filters.size();i++)
        {
            allFilters+= " s."+filters.get(i).getProperty() + " " + filters.get(i).getFilterOperator() +
                    " '" + filters.get(i).getValue()+"'";
            if(i+1 <filters.size())
                allFilters+=" and";
            else
                allFilters+=" ";
        }
        for (int j=0;j<sorts.size();j++)
        {
            allSorts+=" s."+sorts.get(j).getProperty() + " " + sorts.get(0).getSortDirection();
            if(j+1<sorts.size())
                allSorts+=",";
        }
        PanacheQuery<Status> queryStatus = find("select * from statuses s " +
                "where"+ allFilters + "order by" + allSorts).page(page);
        return queryStatus.list();*/
        return  null;

    }


}
