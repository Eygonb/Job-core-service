package com.vega.repositories;

import com.vega.enums.CreationMapper;
import com.vega.enums.Operator;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import com.vega.entities.Status;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class StatusRepository implements PanacheRepositoryBase<Status, Status.StatusKey> {

    public List<Status> findAll(List<Sorter> sorts, List<Filter> filters, Page page, String userId) {
        Map<String, Object> bindValues = getBindingValues(filters, userId);
        String allFilters = createFilter(filters);
        String allSorts = createSorter(sorts);
        return find(allFilters + " order by" + allSorts, bindValues).page(page).list();
    }

    private String createFilter(List<Filter> filters) {
        StringBuilder allFilters = new StringBuilder("from Status s where s.key.userId = :userId");
        CreationMapper mapper = new CreationMapper();
        Map<Operator, String> map = mapper.getMap();
        for (Filter filter : filters) {
            String operator = map.get(filter.getOperator());
            String property = filter.getProperty();

            allFilters.append(" and s.").append(property).append(" ").append(operator).append(" :")
                    .append(property.replaceFirst("key.", ""));
        }
        return allFilters.toString();
    }

    private String createSorter(List<Sorter> sorts) {
        StringBuilder allSorts = new StringBuilder();
        if (sorts.isEmpty()) {
            allSorts.append(" s.orderNum");
        } else {
            for (int j = 0; j < sorts.size(); j++) {
                allSorts.append(" s.").append(sorts.get(j).getProperty()).append(" ").append(sorts.get(j).getSortDirection());
                if (j + 1 < sorts.size())
                    allSorts.append(",");
            }
        }
        return allSorts.toString();
    }

    public Long countStatus(List<Filter> filters, String userId) {
        Map<String, Object> bindValues = getBindingValues(filters, userId);
        String allFilters = createFilter(filters);
        Long count;
        Object o = find("select count(*) " + allFilters, bindValues).firstResult();
        count = (Long) o;
        return count;
    }

    private Map<String, Object> getBindingValues(List<Filter> filters, String userId) {
        Map<String, Object> bindValues = new HashMap<>();

        bindValues.put("userId", userId);
        for (Filter filter : filters) {
            // replace "key." need for fix bug with binding value with "." in name of value
            bindValues.put(filter.getProperty().replaceFirst("key.", ""), filter.getValue());
        }

        return bindValues;
    }

    public Integer getMaxOrderNum() {
        Object count = find("select max(s.orderNum) from Status s").firstResult();
        return (Integer) count;
    }
}

