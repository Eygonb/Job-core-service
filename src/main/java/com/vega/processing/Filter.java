package com.vega.processing;

public class Filter {

    private String property;
    private String value;
    private Operator filterOperator;
    enum Operator{like,equals,notEquals,lessOrEquals,greaterOrEquals,less,greater};

    public Operator getFilterOperator() {
        return filterOperator;
    }

    public void setFilterOperator(Operator filterOperator) {
        this.filterOperator = filterOperator;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }


}
