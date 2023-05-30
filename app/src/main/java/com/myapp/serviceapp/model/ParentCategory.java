package com.myapp.serviceapp.model;

import java.io.Serializable;

public class ParentCategory implements Serializable {
    private String catParentId;
    private String catId;
    private String catParentName;
/*
    private ChildCategory ChildCategory;
*/

    public ParentCategory() {
    }

    public ParentCategory(String catId, String catParentName , String catParentId) {
        this.catId = catId;
        this.catParentName = catParentName;
        this.catParentId=catParentId;
    }


    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
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

    @Override
    public String toString() {
        return catParentName;
    }

    public void setCatParentName(String catParentName) {
        this.catParentName = catParentName;
    }
}
