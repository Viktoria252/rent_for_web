package com.example.rent.dto;

public class ShowDetailedEquipmentInfoDto {
    private String id;
    private String title;
    private String description;
    private String image;
    private Double dailyprice;
    private Double deposit;
    private int rental_count;
    private String category;
    private String brand;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getDailyprice() {
        return dailyprice;
    }

    public void setDailyprice(Double dailyprice) {
        this.dailyprice = dailyprice;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public int getRental_count() {
        return rental_count;
    }

    public void setRental_count(int rental_count) {
        this.rental_count = rental_count;
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
