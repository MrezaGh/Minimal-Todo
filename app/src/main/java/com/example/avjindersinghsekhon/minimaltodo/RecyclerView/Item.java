package com.example.avjindersinghsekhon.minimaltodo.RecyclerView;


public class Item {
    private String title;
    private String subtitle;
    private boolean active;

    public Item(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
        this.active = true;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public boolean isActive() {
        return active;
    }
}