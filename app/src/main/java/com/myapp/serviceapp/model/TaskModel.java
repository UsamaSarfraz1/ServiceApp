package com.myapp.serviceapp.model;

public class TaskModel {
    String taskId;
    String taskTitle;
    String taskDetails;
    String catId;
    String catName;
    String location;
    String budget;
    String date;
    String userId;
    public TaskModel(String taskId,String userId,String taskTitle, String taskDetails, String catId, String catName, String location, String budget, String date) {
        this.taskId = taskId;
        this.userId=userId;
        this.taskDetails = taskDetails;
        this.catId = catId;
        this.catName = catName;
        this.location = location;
        this.budget = budget;
        this.date = date;
        this.taskTitle=taskTitle;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public TaskModel() {
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
