package com.myapp.serviceapp.model;

import java.io.Serializable;

public class ChildCategory implements Serializable {
    private String catId;
    private String name;
    private String description;
    private String catIcon;

    public ChildCategory() {
    }

    public ChildCategory(String catId, String name, String description) {
        this.catId = catId;
        this.name = name;
        this.description = description;
    }

    public ChildCategory(String catId, String name, String description, String catIcon) {
        this.catId = catId;
        this.name = name;
        this.description = description;
        this.catIcon = catIcon;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCatIcon() {
        return catIcon;
    }

    public void setCatIcon(String catIcon) {
        this.catIcon = catIcon;
    }
}
