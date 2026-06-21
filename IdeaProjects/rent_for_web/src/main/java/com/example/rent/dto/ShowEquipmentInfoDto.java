package com.example.rent.dto;

import java.io.Serializable;

public class ShowEquipmentInfoDto implements Serializable {
    private String id;
    private String title;
    private Double dailyprice;
    private Double deposit;
    private String image;
    private String description;
    private String category;
    private String brand;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getDailyprice() {
        return dailyprice;
    }

    public void setDailyprice(Double dailyprice) {
        this.dailyprice = dailyprice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
