package com.vega.repositories;

import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import com.vega.entities.Event;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EventRepository implements PanacheRepositoryBase<Event, UUID> {
    @Inject
    EntityManager entityManager;

    public List<Event> findAll(List<Sorter> sorts, List<Filter> filters, Page page)
    {
        String allFilters ="";
        String allSorts ="";
        for(int i=0;i<filters.size();i++)
        {
            allFilters+= " ev."+filters.get(i).getProperty() + " " + filters.get(i).getFilterOperator() +
                    " '" + filters.get(i).getValue()+"'";
            if(i+1 <filters.size())
                allFilters+=" and";
            else
                allFilters+=" ";
        }
        for (int j=0;j<sorts.size();j++)
        {
            allSorts+=" ev."+sorts.get(j).getProperty() + " " + sorts.get(0).getSortDirection();
            if(j+1<sorts.size())
                allSorts+=",";
        }
        PanacheQuery<Event> queryEvent = find("select * from events ev " +
                "where"+ allFilters + "order by" + allSorts).page(page);
        return queryEvent.list();
    }

    public Event findByIdAndUserId(UUID id, String userId) {
        return find("id = ?1 and user_id = ?2", id, userId).firstResult();
    }

    public List<Event> findByUserId(String userId) {
        return list("user_id", userId);
    }

    @SuppressWarnings("unchecked")
    public List<Event> findEventsWithCurrentNotification() {
        return entityManager.createNativeQuery(
                        "SELECT e.* " +
                                "FROM events e " +
                                "WHERE (begin_date - CAST(notify_for || ' minutes' AS INTERVAL)) = ?", Event.class)
                .setParameter(1, ZonedDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .getResultList();
    }
}

