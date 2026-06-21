package com.example.rent.services;

import com.example.rent.dto.ShowDetailedUserInfoDto;
import com.example.rent.dto.ShowUserInfoDto;
import com.example.rent.dto.UserRegistrationDto;
import com.example.rent.models.entities.User;
import com.example.rent.models.enums.UserRoles;
import com.example.rent.models.exceptions.UserNotFoundException;
import com.example.rent.repositories.UserRepository;
import com.example.rent.repositories.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"users", "userProfiles", "allUsers"}, allEntries = true)
    public void registerUser(UserRegistrationDto userRegistrationDto) {
        log.debug("Регистрация нового пользователя: {}", userRegistrationDto.getUsername());

        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Пароли не совпадают");
        }

        if (userRepository.findByUsername(userRegistrationDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким Username уже существует");
        }

        User user = new User(
                userRegistrationDto.getUsername(),
                passwordEncoder.encode(userRegistrationDto.getPassword()),
                userRegistrationDto.getFullname(),
                userRegistrationDto.getBudget()
        );

        var userRole = roleRepository.findRoleByName(UserRoles.USER).orElseThrow();

        user.setRoles(List.of(userRole));

        userRepository.save(user);
        log.info("Пользователь успешно зарегистрирован: {}", user.getUsername());
    }


    @Override
    public User getUser(String username) {
        log.debug("Поиск пользователя по Username: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден: " + username));
    }


    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "users", key = "#userId"),
            @CacheEvict(value = "userProfiles", key = "#userId"),
            @CacheEvict(value = "allUsers", allEntries = true)
    })
    public void updateUserBudget(String userId, Double newBudget) {
        log.debug("Обновление бюджета пользователя {}: {}", userId, newBudget);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден: " + userId));

        if (newBudget < 0) {
            throw new IllegalArgumentException("Бюджет не может быть отрицательным");
        }

        user.setBudget(newBudget);
        userRepository.save(user);
        log.info("Бюджет пользователя {} обновлен: {}", user.getUsername(), newBudget);
    }

    @Override
    @Cacheable(value = "allUsers", key = "'all'")
    public List<ShowUserInfoDto> getAllUsers() {
        log.debug("Получение списка всех пользователей");
        return userRepository.findAll().stream()
                .map(user -> mapper.map(user, ShowUserInfoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"users", "userProfiles", "allUsers", "currentUser"}, allEntries = true)
    public void deleteUser(String userId) {
        log.debug("Удаление пользователя: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден: " + userId));
        userRepository.delete(user);
        log.info("Пользователь удален: {}", user.getUsername());
    }

//    @Override
//    @Cacheable(value = "userRoles", key = "#user.id + '_admin'")
//    public boolean isAdmin(User user) {
//        log.debug("Проверка роли ADMIN для пользователя: {}", user.getUsername());
//        return user.getRoles().stream()
//                .anyMatch(role -> role.getName() == UserRoles.ADMIN);
//    }
//
//    @Override
//    @Cacheable(value = "userRoles", key = "#user.id + '_moderator'")
//    public boolean isModerator(User user) {
//        log.debug("Проверка роли MODERATOR для пользователя: {}", user.getUsername());
//        return user.getRoles().stream()
//                .anyMatch(role -> role.getName() == UserRoles.MODERATOR || role.getName() == UserRoles.ADMIN);
//    }
}
