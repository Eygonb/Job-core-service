package com.vega.repositories;

import com.vega.entities.Vacancy;
import com.vega.enums.CreationMapper;
import com.vega.enums.Operator;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

@ApplicationScoped
public class VacancyRepository implements PanacheRepositoryBase<Vacancy, UUID> {
    public List<Vacancy> findAll(List<Sorter> sorts, List<Filter> filters, Page page, String userId) {
        Map<String, Object> bindValues = getBindingValues(filters, userId);
        String allFilters = createFilter(filters);
        String allSorts = createSorter(sorts);
        return find(allFilters + " order by" + allSorts, bindValues).page(page).list();
    }

    private String createFilter(List<Filter> filters) {
        StringBuilder allFilters = new StringBuilder("from Vacancy v where v.userId = :userId");
        CreationMapper mapper = new CreationMapper();
        Map<Operator, String> map = mapper.getMap();
        for (Filter filter : filters) {
            String operator = map.get(filter.getOperator());
            String property = filter.getProperty();

            allFilters.append(" and v.").append(property).append(" ").append(operator).append(" :").append(property);
        }
        return allFilters.toString();
    }

    private String createSorter(List<Sorter> sorts) {
        StringBuilder allSorts = new StringBuilder();
        if (sorts.isEmpty()) {
            allSorts.append(" v.id");
        } else {
            for (int j = 0; j < sorts.size(); j++) {
                allSorts.append(" v.").append(sorts.get(j).getProperty()).append(" ").append(sorts.get(j).getSortDirection());
                if (j + 1 < sorts.size())
                    allSorts.append(",");
            }
        }
        return allSorts.toString();
    }

    public Long countVacancy(List<Filter> filters, String userId) {
        Map<String, Object> bindValues = getBindingValues(filters, userId);
        String allFilters = createFilter(filters);
        Long count;
        Object o = find("select count(*) " + allFilters, bindValues).firstResult();
        count = (Long) o;
        return count;
    }

    public Vacancy findByIdAndUserId(UUID id, String userId) {
        return find("id = ?1 and user_id = ?2", id, userId).firstResult();
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
