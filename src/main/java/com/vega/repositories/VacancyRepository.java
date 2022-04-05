package com.vega.repositories;

import com.vega.entities.Vacancy;
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
       // String userId = "6789tr236h8tv6fgh";
        String allFilters = "where v.userId ='6789tr236h8tv6fgh'";
        String allSorts = "";
        Object[] values = new Object[filters.size()];

        for (int i = 0; i < filters.size(); i++) {
            allFilters += " v." + filters.get(i).getProperty() + " " + filters.get(i).getFilterOperator() + "?" + i;
            values[i] = filters.get(i).getValue();
            if (i + 1 < filters.size())
                allFilters += " and";
            else
                allFilters += " ";
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
            PanacheQuery<Vacancy> queryVacancy = find("from Vacancies v " +
                    allFilters + "order by" + allSorts, values).page(page);
            return queryVacancy.list();
        } else
            return find("from Vacancy v " +
                    allFilters + "order by" + allSorts).page(page).list();
    }


}
