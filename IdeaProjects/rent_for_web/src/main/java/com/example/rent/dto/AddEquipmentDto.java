package com.example.rent.dto;

import com.example.rent.utils.validation.UniqueTitle;
import jakarta.validation.constraints.*;

public class AddEquipmentDto {

    @UniqueTitle
    private String title;

    private String description;

    private int rental_count;

    private double dailyprice;

    private double deposit;

    private String image;

    private String brand;
    private String category;
    @NotEmpty(message = "Название не должно быть пустым!")
    @Size(min = 2, message = "Название должно содержать не менее 2 символов!")

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotEmpty(message = "Описание не должно быть пустым!")
    @Size(min = 10, message = "Описание должно содержать не менее 10 символов!")

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Количество аренд не должно быть пустым!")
    @Min(value = 0, message = "Количество аренд должно быть положительным числом!")

    public int getRental_count() {
        return rental_count;
    }

    public void setRental_count(int rental_count) {
        this.rental_count = rental_count;
    }

    @NotNull(message = "Цена не должна быть пустой!")
    @DecimalMin(value = "0.01", message = "Цена должна быть положительным числом!")
    public double getDailyprice() {
        return dailyprice;
    }

    public void setDailyprice(double dailyprice) {
        this.dailyprice = dailyprice;
    }

    @NotNull(message = "Депозит не должна быть пустой!")
    @DecimalMin(value = "0.01", message = "Депозит должна быть положительным числом!")
    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    @NotNull(message = "Картинка не должна быть пустой!")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @NotEmpty(message = "Бренд не должен быть пустым!")
    @Size(min = 2, message = "Бренд должен содержать не менее 2 символов!")

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @NotEmpty(message = "Категория не должна быть пустым!")
    @Size(min = 2, message = "Категория должна содержать не менее 2 символов!")

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
