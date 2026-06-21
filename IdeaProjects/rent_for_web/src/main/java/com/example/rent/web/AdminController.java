//package com.example.rent.web;
//
//import com.example.rent.services.EquipmentService;
//import com.example.rent.services.OrderService;
//import com.example.rent.services.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Slf4j
//@Controller
//@RequestMapping("/admin")
//@PreAuthorize("hasRole('ADMIN')")
//public class AdminController {
//
//    private final UserService userService;
//    private final OrderService orderService;
//    private final EquipmentService equipmentService;
//
//    public AdminController(UserService userService, OrderService orderService, EquipmentService equipmentService) {
//        this.userService = userService;
//        this.orderService = orderService;
//        this.equipmentService = equipmentService;
//        log.info("AdminController инициализирован");
//    }
//
//    @GetMapping("/dashboard")
//    public String showDashboard(Model model) {
//        log.debug("Отображение админ панели");
//
//        // Статистика для дашборда
//        try {
//            var allUsers = userService.getAllUsers();
//            var allEquipments = equipmentService.allEquipments();
//
//            model.addAttribute("totalUsers", allUsers.size());
//            model.addAttribute("totalEquipments", allEquipments.size());
//            // Можно добавить больше статистики
//
//            return "admin-dashboard";
//        } catch (Exception e) {
//            log.warn("Ошибка загрузки дашборда: {}", e.getMessage());
//            return "redirect:/";
//        }
//    }
//
//    @GetMapping("/users")
//    public String showUserManagement() {
//        log.debug("Перенаправление на управление пользователями");
//        return "redirect:/users/all";
//    }
//
//    @GetMapping("/orders")
//    public String showOrderManagement() {
//        log.debug("Перенаправление на управление заказами");
//        return "redirect:/orders/all";
//    }
//
//    @GetMapping("/equipments")
//    public String showEquipmentManagement() {
//        log.debug("Перенаправление на управление оборудованием");
//        return "redirect:/equipments/all";
//    }
//}