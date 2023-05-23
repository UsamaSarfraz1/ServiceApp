package com.myapp.serviceapp.model;

public class ParentCategory {
    private String catParentId;
    private String catParentName;
    private ChildCategory ChildCategory;

    public ParentCategory() {
    }

    public ParentCategory(String catParentId, String catParentName) {
        this.catParentId = catParentId;
        this.catParentName = catParentName;
    }

    public ParentCategory(String catParentId, String catParentName, ChildCategory childCategory) {
        this.catParentId = catParentId;
        this.catParentName = catParentName;
        ChildCategory = childCategory;
    }

    public ChildCategory getChildCategory() {
        return ChildCategory;
    }

    public void setChildCategory(ChildCategory childCategory) {
        ChildCategory = childCategory;
    }

    public String getCatParentId() {
        return catParentId;
    }

    public void setCatParentId(String catParentId) {
        this.catParentId = catParentId;
    }

    public String getCatParentName() {
        return catParentName;
    }

    public void setCatParentName(String catParentName) {
        this.catParentName = catParentName;
    }
}
