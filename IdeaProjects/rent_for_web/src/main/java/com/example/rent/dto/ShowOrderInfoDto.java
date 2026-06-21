package com.example.rent.dto;

import com.example.rent.models.entities.Equipment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShowOrderInfoDto {
    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalCost;

    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

}
