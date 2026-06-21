package com.example.rent.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ShowUserLoginDto {

    @NotEmpty(message = "Username не может быть пустым")
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым")
    @Size(min = 5, message = "Пароль должен содержать не менее 5 символов")
    private String password;

    public ShowUserLoginDto() {
    }

    public ShowUserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}