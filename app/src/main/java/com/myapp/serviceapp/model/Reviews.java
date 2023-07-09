package com.myapp.serviceapp.model;

public class Reviews {
    private double rating;
    private String comment;

    public Reviews() {
    }

    public Reviews(double rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }



}

