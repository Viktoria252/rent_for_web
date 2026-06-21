package com.example.rent.views;

import java.io.Serializable;

public class UserProfileView implements Serializable {
    private String fullName;
    private String username;
    private Double budget;

    public UserProfileView(String fullName, String username, Double budget) {
        this.fullName = fullName;
        this.username = username;
        this.budget = budget;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }
}