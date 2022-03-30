package com.vega.processing;

public class Sorter {

    private String property;

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    //private Direction sortDirection;
    private String sortDirection;

    enum Direction{ASC,DESC};

   // public Direction getSortDirection() {
    //    return sortDirection;
   // }

   /* public void setSortDirection(Direction sortDirection) {
        this.sortDirection = sortDirection;
    }*/


    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
