package com.vega.repositories;

import com.vega.enums.CreationMapper;
import com.vega.enums.Operator;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import com.vega.entities.Event;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class EventRepository implements PanacheRepositoryBase<Event, UUID> {
    @Inject
    EntityManager entityManager;

    public List<Event> findAll(List<Sorter> sorts, List<Filter> filters, Page page, String userId) {
        Object[] values = new Object[filters.size()+1];
        String allFilters = createFilter(filters, userId, values);
        String allSorts = createSorter(sorts);
        return find(allFilters + " order by" + allSorts, values).page(page).list();
    }

    private String createFilter(List<Filter> filters, String userId, Object[] values) {
        StringBuilder allFilters = new StringBuilder("from Event ev where ev.userId = ?1");
        values[0] = userId;
        CreationMapper mapper = new CreationMapper();
        Map<Operator, String> map = mapper.getMap();
        for (int i = 0; i < filters.size(); i++) {
            String operator;
            operator = map.get(filters.get(i).getFilterOperator());
            allFilters.append(" and ev.").append(filters.get(i).getProperty()).append(" ").append(operator).append(" ?").append(i + 2);
            values[i+1] = filters.get(i).getValue();
        }
        return allFilters.toString();
    }

    private String createSorter(List<Sorter> sorts) {
        StringBuilder allSorts = new StringBuilder();
        if (sorts.size() == 0) {
            allSorts.append(" ev.id");
        }
        else {
            for (int j = 0; j < sorts.size(); j++) {
                allSorts.append(" ev.").append(sorts.get(j).getProperty()).append(" ").append(sorts.get(j).getSortDirection());
                if (j + 1 < sorts.size())
                    allSorts.append(",");
            }
        }
        return  allSorts.toString();
    }

    public Long countEvent(List<Filter> filters, String userId){
        Long count;
        Object[] values = new Object[filters.size()+1];
        String allFilters = createFilter(filters, userId, values);
        Object o = find("select count(*) " + allFilters, values).firstResult();
        count = (Long) o;
        return count;
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

