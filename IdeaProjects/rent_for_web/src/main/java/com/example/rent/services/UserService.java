package com.example.rent.services;

import com.example.rent.dto.ShowDetailedUserInfoDto;
import com.example.rent.dto.ShowUserInfoDto;
import com.example.rent.dto.UserRegistrationDto;
import com.example.rent.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void registerUser(UserRegistrationDto userRegistrationDto);
    User getUser(String username);
    void updateUserBudget(String userId, Double newBudget);
    List<ShowUserInfoDto> getAllUsers();
    void deleteUser(String userId);
}
