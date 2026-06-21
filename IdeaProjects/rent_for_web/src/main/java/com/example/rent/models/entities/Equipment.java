package com.example.rent.models.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "equipments")
public class Equipment extends BaseEntity {

    @Column(nullable = false)
    private int rentalCount;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String description;

    @Column(columnDefinition = "DECIMAL(19,2)", nullable = false)
    private Double dailyPrice;

    @Column(columnDefinition = "DECIMAL(19,2)", nullable = false)
    private Double deposit;

    @Column(nullable = false)
    private String image;

    public Equipment() {
    }

    public boolean isAvailable(LocalDate startDate, LocalDate endDate) {
        return true;
    }

    // Getters and Setters
    public int getRentalCount() {
        return rentalCount;
    }

    public void setRentalCount(int rental_count) {
        this.rentalCount = rental_count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(Double dailyprice) {
        this.dailyPrice = dailyprice;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void addRentalCount(){this.rentalCount++;}

}