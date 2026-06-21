//package com.example.rent.web;
//
//import com.example.rent.dto.ShowUserInfoDto;
//import com.example.rent.dto.UserRegistrationDto;
//import com.example.rent.models.entities.User;
//import com.example.rent.services.UserService;
//import com.example.rent.views.UserProfileView;
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.security.Principal;
//import java.util.List;
//
//@Slf4j
//@Controller
//@RequestMapping("/users")
//public class UserController {
//
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//        log.info("UserController инициализирован");
//    }
//
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        log.debug("Отображение формы регистрации");
//        if (!model.containsAttribute("userRegistrationDto")) {
//            model.addAttribute("userRegistrationDto", new UserRegistrationDto());
//        }
//        return "user-register";
//    }
//
//    @PostMapping("/register")
//    public String registerUser(@Valid @ModelAttribute UserRegistrationDto userRegistrationDto,
//                               BindingResult bindingResult,
//                               RedirectAttributes redirectAttributes) {
//        log.debug("Обработка регистрации пользователя: {}", userRegistrationDto.getUsername());
//
//        if (bindingResult.hasErrors()) {
//            log.warn("Ошибки валидации при регистрации: {}", bindingResult.getAllErrors());
//            redirectAttributes.addFlashAttribute("userRegistrationDto", userRegistrationDto);
//            redirectAttributes.addFlashAttribute(
//                    "org.springframework.validation.BindingResult.userRegistrationDto",
//                    bindingResult);
//            return "redirect:/users/register";
//        }
//
//        try {
//            userService.registerUser(userRegistrationDto);
//            redirectAttributes.addFlashAttribute("successMessage",
//                    "Регистрация прошла успешно! Теперь вы можете войти в систему.");
//            return "redirect:/login";
//        } catch (IllegalArgumentException e) {
//            log.warn("Ошибка регистрации: {}", e.getMessage());
//            redirectAttributes.addFlashAttribute("userRegistrationDto", userRegistrationDto);
//            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
//            return "redirect:/users/register";
//        }
//    }
//
//    @GetMapping("/profile")
//    public String showUserProfile(Principal principal, Model model) {
//        log.debug("Отображение профиля пользователя");
//        String username = principal.getName();
//        User user = userService.getUser(username);
//        UserProfileView userProfile = new UserProfileView(
//                user.getFullName(),
//                username,
//                user.getBudget()
//        );
//        model.addAttribute("userProfile", userProfile);
//        return "user-profile";
//    }
//
//    @GetMapping("/login")
//    public String showLoginForm() {
//        return "login";
//    }
//    @PostMapping("/login-error")
//    public String onFailedLogin(
//            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
//            RedirectAttributes redirectAttributes) {
//
//        log.warn("Неудачная попытка входа для пользователя: {}", username);
//        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
//        redirectAttributes.addFlashAttribute("badCredentials", true);
//
//        return "redirect:/login";
//    }
//
//    @GetMapping("/all")
//    public String showAllUsers(Model model) {
//        log.debug("Отображение списка всех пользователей (админ)");
//        List<ShowUserInfoDto> users = userService.getAllUsers();
//        model.addAttribute("users", users);
//        return "user-all";
//    }
//
//    @PostMapping("/delete/{userId}")
//    public String deleteUser(@PathVariable String userId, RedirectAttributes redirectAttributes) {
//        log.debug("Удаление пользователя: {}", userId);
//        try {
//            userService.deleteUser(userId);
//            redirectAttributes.addFlashAttribute("successMessage", "Пользователь успешно удален!");
//        } catch (Exception e) {
//            log.warn("Ошибка удаления пользователя: {}", e.getMessage());
//            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
//        }
//        return "redirect:/users/all";
//    }
//}
