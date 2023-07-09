package com.myapp.serviceapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskModel implements Serializable {
    private String taskId;
    private String taskTitle;
    private String taskDetails;
    private String catId;
    private String catName;
    private String location;
    private String budget;
    private String date;
    private String userId;
    private String status;
    private String assignUser;
    private List<Offers> orderlist=new ArrayList<>();
    public TaskModel(String taskId,String userId,String taskTitle, String taskDetails, String catId, String catName, String location, String budget, String date, String status, String assignUser) {
        this.taskId = taskId;
        this.userId=userId;
        this.taskDetails = taskDetails;
        this.catId = catId;
        this.catName = catName;
        this.location = location;
        this.budget = budget;
        this.date = date;
        this.taskTitle=taskTitle;
        this.status=status;
        this.assignUser=assignUser;
    }

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
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

    public void assign() {
        if (status.equals("open")) {
            status = "assigned";
            System.out.println("Task assigned.");
        } else {
            System.out.println("Task cannot be assigned as it is already in progress or completed.");
        }
    }

    public void complete() {
        if (status.equals("assigned")) {
            status = "completed";
            System.out.println("Task completed.");
        } else {
            System.out.println("Task cannot be marked as completed as it is not yet assigned or already completed.");
        }
    }

    public void review() {
        if (status.equals("completed")) {
            status = "reviewed";
            System.out.println("Task reviewed.");
        } else {
            System.out.println("Task cannot be reviewed as it is not yet completed or already reviewed.");
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Offers> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<Offers> orderlist) {
        this.orderlist = orderlist;
    }
}
