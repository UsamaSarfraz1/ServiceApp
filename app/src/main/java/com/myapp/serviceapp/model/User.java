package com.myapp.serviceapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User implements Serializable {
    private String userId;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String cnic;
    private String role;
    private String status;
    private Double userAverageRating;
    private String cnicUrl;
    private HashMap<String,Reviews> reviewsList;

    public User() {
        // Empty constructor required for Firebase
    }

    public User(String userId, String name, String email, String address, String phone, String cnic,String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.cnic = cnic;
        this.role = role;
        this.reviewsList=new HashMap<>();
        this.status="open";
    }


    public String getCnicUrl() {
        return cnicUrl;
    }

    public void setCnicUrl(String cnicUrl) {
        this.cnicUrl = cnicUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String, Reviews> getReviewsList() {
        return reviewsList;
    }

    public void setReviewsList(HashMap<String , Reviews> reviewsList) {
        this.reviewsList = reviewsList;
    }

    public Double getUserAverageRating() {
        return userAverageRating;
    }

    public void setUserAverageRating(Double userAverageRating) {
        this.userAverageRating = userAverageRating;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public double getAverageRating() {
        if (reviewsList == null || reviewsList.isEmpty()) {
            return 0.0; // Return 0 if there are no reviews
        }

        double sum = 0.0;
        int count = 0;

        for (Reviews review : reviewsList.values()) {
            sum += review.getRating();
            count++;
        }

        return sum / count;
    }

}
