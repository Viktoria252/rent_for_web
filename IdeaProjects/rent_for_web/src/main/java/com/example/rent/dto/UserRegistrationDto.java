package com.example.rent.dto;

import com.example.rent.utils.validation.UniqueUsername;
import jakarta.validation.constraints.*;

public class UserRegistrationDto {

    private String fullname;


    private String username;


    private double budget;


    private String password;


    private String confirmPassword;

    public UserRegistrationDto() {}

    @NotEmpty(message = "Полное имя не должно быть пустым!")
    @Size(min = 5, max = 20, message = "Полное имя должно быть от 5 до 20 символов!")
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    @NotEmpty(message = "Username не должен быть пустым!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Min(value = 0, message = "Бюджет не может быть меньше 0!")
    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }
    @NotEmpty(message = "Пароль не должен быть пустым!")
    @Size(min = 5, max = 20, message = "Пароль должен быть от 5 до 20 символов!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @NotEmpty(message = "Подтверждение пароля не должно быть пустым!")
    @Size(min = 5, max = 20, message = "Подтверждение пароля должно быть от 5 до 20 символов!")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                ", fullName='" + fullname + '\'' +
                ", username='" + username + '\'' +
                ", budget=" + budget +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
