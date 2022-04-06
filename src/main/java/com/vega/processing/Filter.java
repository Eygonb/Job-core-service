package com.vega.processing;

import com.vega.enums.Operator;

public class Filter {

    private String property;
    private Object value;
    private Operator filterOperator;

    public Operator getFilterOperator() {
        return filterOperator;
    }

    public void setFilterOperator(Operator filterOperator) {
        this.filterOperator = filterOperator;
    }


    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }




}
