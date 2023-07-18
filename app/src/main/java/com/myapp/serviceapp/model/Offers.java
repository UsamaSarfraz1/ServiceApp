package com.myapp.serviceapp.model;

import java.io.Serializable;

public class Offers implements Serializable {
    private String offer_id;
    private String offer_reviews;
    private double offer_rating;
    private String offer_phone;
    private String freelancerName;
    private String freelancer_id;
    private boolean isAssigned;
    private boolean isCompleted;
    private boolean isReviewed;


    public Offers() {
    }

    public Offers(String offer_id, String offer_reviews, double offer_rating, String offer_phone, String freelancerName, String freelancer_id, Boolean isAssigned, Boolean isCompleted,Boolean isReviewed) {
        this.offer_id = offer_id;
        this.offer_reviews = offer_reviews;
        this.offer_rating = offer_rating;
        this.offer_phone = offer_phone;
        this.freelancerName = freelancerName;
        this.freelancer_id = freelancer_id;
        this.isAssigned=isAssigned;
        this.isCompleted=isCompleted;
        this.isReviewed=isReviewed;
    }


    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getOffer_reviews() {
        return offer_reviews;
    }

    public void setOffer_reviews(String offer_reviews) {
        this.offer_reviews = offer_reviews;
    }

    public double getOffer_rating() {
        return offer_rating;
    }

    public void setOffer_rating(double offer_rating) {
        this.offer_rating = offer_rating;
    }

    public String getOffer_phone() {
        return offer_phone;
    }

    public void setOffer_phone(String offer_phone) {
        this.offer_phone = offer_phone;
    }

    public String getFreelancerName() {
        return freelancerName;
    }

    public void setFreelancerName(String freelancerName) {
        this.freelancerName = freelancerName;
    }

    public String getFreelancer_id() {
        return freelancer_id;
    }

    public void setFreelancer_id(String freelancer_id) {
        this.freelancer_id = freelancer_id;
    }
}
