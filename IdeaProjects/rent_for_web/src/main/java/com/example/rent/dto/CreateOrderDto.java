package com.example.rent.dto;

import java.time.LocalDate;

public class CreateOrderDto {
    private String equipmentId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalCost;

    public CreateOrderDto() {}

    public CreateOrderDto(String equipmentId, LocalDate startDate, LocalDate endDate, Double totalCost) {
        this.equipmentId = equipmentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
    }

    // геттеры и сеттеры
    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
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

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
}




//package com.example.rent.dto;
//
//import jakarta.validation.constraints.Future;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public class CreateOrderDto{
//
//    private String equipmentId;
//    private Double dailyPrice;
//    private Double deposit;
//    private LocalDate startDate;
//    private LocalDate endDate;
//    private long daysCount;
//    private Double rentalCost;
//    private Double totalCost;
//
//    public CreateOrderDto(String equipmentId, Double dailyPrice, Double deposit, LocalDate startDate, LocalDate endDate, long daysCount, Double rentalCost, Double totalCost) {
//        this.equipmentId = equipmentId;
//        this.dailyPrice = dailyPrice;
//        this.deposit = deposit;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.daysCount = daysCount;
//        this.rentalCost = rentalCost;
//        this.totalCost = totalCost;
//    }
//    public CreateOrderDto(){};
//
//    public String getEquipmentId() {
//        return equipmentId;
//    }
//
//    public void setEquipmentId(String equipmentId) {
//        this.equipmentId = equipmentId;
//    }
//
//    public Double getDailyPrice() {
//        return dailyPrice;
//    }
//
//    public void setDailyPrice(Double dailyPrice) {
//        this.dailyPrice = dailyPrice;
//    }
//
//    public Double getDeposit() {
//        return deposit;
//    }
//
//    public void setDeposit(Double deposit) {
//        this.deposit = deposit;
//    }
//
//    public LocalDate getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(LocalDate startDate) {
//        this.startDate = startDate;
//    }
//
//    public LocalDate getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(LocalDate endDate) {
//        this.endDate = endDate;
//    }
//
//    public long getDaysCount() {
//        return daysCount;
//    }
//
//    public void setDaysCount(long daysCount) {
//        this.daysCount = daysCount;
//    }
//
//    public Double getRentalCost() {
//        return rentalCost;
//    }
//
//    public void setRentalCost(Double rentalCost) {
//        this.rentalCost = rentalCost;
//    }
//
//    public Double getTotalCost() {
//        return totalCost;
//    }
//
//    public void setTotalCost(Double totalCost) {
//        this.totalCost = totalCost;
//    }
//}
