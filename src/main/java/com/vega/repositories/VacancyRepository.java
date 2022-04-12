package com.vega.repositories;

import com.vega.entities.Vacancy;
import com.vega.enums.Operator;
import com.vega.processing.Filter;
import com.vega.processing.Sorter;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;


import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class VacancyRepository implements PanacheRepositoryBase<Vacancy, UUID> {

    public List<Vacancy> findAll(List<Sorter> sorts, List<Filter> filters, Page page) {
        return queryVacancy(sorts, filters).page(page).list();
    }

    private PanacheQuery<Vacancy> queryVacancy(List<Sorter> sorts, List<Filter> filters) {
        // String userId = "6789tr236h8tv6fgh";
        String allFilters = "where v.userId ='6789tr236h8tv6fgh'";
        String allSorts = "";
        Object[] values = new Object[filters.size()];

        for (int i = 0; i < filters.size(); i++) {
            String operator = "";
            if (filters.get(i).getFilterOperator() == Operator.EQUALS) {
                operator = "=";
            } else if (filters.get(i).getFilterOperator() == Operator.LESS) {
                operator = "<";
            } else if (filters.get(i).getFilterOperator() == Operator.GREATER) {
                operator = ">";
            } else
                operator = "LIKE";
            allFilters += " and v." + filters.get(i).getProperty() + " " + operator + " ?" + (i + 1);
            values[i] = filters.get(i).getValue();
        }
        for (int j = 0; j < sorts.size(); j++) {
            allSorts += " v." + sorts.get(j).getProperty() + " " + sorts.get(j).getSortDirection();
            if (j + 1 < sorts.size())
                allSorts += ",";
        }
        if (sorts.size() == 0) {
            allSorts += " v.id";
        }

        if (filters.size() > 0) {
            //  PanacheQuery<Vacancy> queryVacancy = find("from Vacancy v " +
            // allFilters + "order by" + allSorts, values).page(page);
            return find("from Vacancy v " +
                    allFilters + " order by" + allSorts, values);
        } else
            return find("from Vacancy v " +
                    allFilters + "order by" + allSorts);
    }

    public int countVacancy(List<Sorter> sorts, List<Filter> filters) {
        return queryVacancy(sorts, filters).list().size();
    }

    public Vacancy findByIdAndUserId(UUID id, String userId) {
        return find("id = ?1 and user_id = ?2", id, userId).firstResult();
    }



}
