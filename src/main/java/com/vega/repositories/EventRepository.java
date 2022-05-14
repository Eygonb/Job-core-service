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
import java.util.*;

@ApplicationScoped
public class EventRepository implements PanacheRepositoryBase<Event, UUID> {
    @Inject
    EntityManager entityManager;

    public List<Event> findAll(List<Sorter> sorts, List<Filter> filters, Page page, String userId) {
        Map<String, Object> bindValues = getBindingValues(filters, userId);
        String allFilters = createFilter(filters);
        String allSorts = createSorter(sorts);
        return find(allFilters + " order by" + allSorts, bindValues).page(page).list();
    }

    private String createFilter(List<Filter> filters) {
        StringBuilder allFilters = new StringBuilder("from Event ev where ev.userId = :userId");
        CreationMapper mapper = new CreationMapper();
        Map<Operator, String> map = mapper.getMap();
        for (Filter filter : filters) {
            String operator = map.get(filter.getOperator());
            String property = filter.getProperty();

            allFilters.append(" and ev.").append(property).append(" ").append(operator).append(" :").append(property);
        }
        return allFilters.toString();
    }

    private String createSorter(List<Sorter> sorts) {
        StringBuilder allSorts = new StringBuilder();
        if (sorts.isEmpty()) {
            allSorts.append(" ev.id");
        } else {
            for (int j = 0; j < sorts.size(); j++) {
                allSorts.append(" ev.").append(sorts.get(j).getProperty()).append(" ").append(sorts.get(j).getSortDirection());
                if (j + 1 < sorts.size())
                    allSorts.append(",");
            }
        }
        return allSorts.toString();
    }

    public Long countEvent(List<Filter> filters, String userId) {
        Map<String, Object> bindValues = getBindingValues(filters, userId);
        String allFilters = createFilter(filters);
        Long count;
        Object o = find("select count(*) " + allFilters, bindValues).firstResult();
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

    private Map<String, Object> getBindingValues(List<Filter> filters, String userId) {
        Map<String, Object> bindValues = new HashMap<>();

        bindValues.put("userId", userId);
        for (Filter filter : filters) {
            try {
                UUID uuid = UUID.fromString((String) filter.getValue());
                bindValues.put(filter.getProperty(), uuid);
            } catch (IllegalArgumentException ex) {
                bindValues.put(filter.getProperty(), filter.getValue());
            }
        }

        return bindValues;
    }
}

