package com.myapp.serviceapp.model;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String cnic;
    private String role;

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
}
