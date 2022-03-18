package com.vega.processing;

public class Sorter {

    private String property;

    Direction sortDirection;

    enum Direction{ASC,DESC};

    public Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Direction sortDirection) {
        this.sortDirection = sortDirection;
    }


    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
