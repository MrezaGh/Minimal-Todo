package com.example.avjindersinghsekhon.minimaltodo.Utility;

public class SortConstraints {
    private String sortBy;
    private boolean isIncrease;

    public void setIncrease() {
        isIncrease = true;
    }

    public boolean isIncrease() {
        return isIncrease;
    }

    public void setDecrease() {
        isIncrease = false;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortByDate() {
        this.sortBy = "date";
    }

    public void setSortByImportance() {
        this.sortBy = "importance";
    }

    public void setSortByCreateDate() {
        this.sortBy = "create time";
    }
}
