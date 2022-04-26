package com.vega.processing;

import com.vega.enums.Direction;

public class Sorter {
    private String property;

    private Direction sortDirection;

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
