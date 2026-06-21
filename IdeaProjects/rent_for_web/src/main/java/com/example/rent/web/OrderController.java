package com.example.rent.web;

import com.example.rent.dto.*;
import com.example.rent.models.entities.User;
import com.example.rent.services.EquipmentService;
import com.example.rent.services.OrderService;
import com.example.rent.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import com.example.rent.dto.CreateOrderDto;
import com.example.rent.dto.ShowOrderInfoDto;
import com.example.rent.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody String jsonBody) {
        log.debug("Получен сырой JSON: {}", jsonBody);
        try {
            CreateOrderDto orderDto = objectMapper.readValue(jsonBody, CreateOrderDto.class);

            String testUserId = "c6316de4-0a68-4078-aab0-382842c7b560";
            orderService.createOrder(orderDto, testUserId);
            return ResponseEntity.ok("Заказ создан успешно");
        } catch (Exception e) {
            log.error("Ошибка создания заказа", e);
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }
    @GetMapping("/history")
    public ResponseEntity<List<ShowOrderInfoDto>> getOrderHistory() {
        log.debug("Получение истории заказов");
        try {
            User demoUser = userService.getUser("demo_user");

            List<ShowOrderInfoDto> orders = orderService.getUserOrders(demoUser.getId());

            log.info("Найдено заказов: {}", orders.size());
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            log.error("Ошибка получения истории", e);
            return ResponseEntity.badRequest().build();
        }
    }
}


//@Slf4j
//@RestController
//@RequestMapping("/orders")
//public class OrderController {
//
//    private final OrderService orderService;
//    private final UserService userService;
//    private final EquipmentService equipmentService;
//
//    public OrderController(OrderService orderService, UserService userService, EquipmentService equipmentService){
//        this.orderService = orderService;
//        this.userService = userService;
//        this.equipmentService = equipmentService;
//        log.info("OrderController инициализирован");
//    }

//    @GetMapping("/create")
//    public String createOrder(@RequestParam String equipmentId,
//                              @RequestParam LocalDate startDate,
//                              @RequestParam LocalDate endDate,
//                              Model model) {
//        log.debug("Отображение формы добавления товара");
//        Equipment equipment = equipmentService.findById(equipmentId);   // находим нужный товар по его id
//        long daysCount = ChronoUnit.DAYS.between(startDate, endDate);   // считаем количество дней аренды
//        Double rentalCost = equipment.getDailyPrice() * daysCount;      // высчитываем сумму аренды товара
//        Double totalCost = rentalCost + equipment.getDeposit();         // высчитываем общую сумму заказа
//        // создаем Dto для передачи данных
//        CreateOrderDto orderDetails = new CreateOrderDto(
//                equipmentId,
//                equipment.getDailyPrice(),
//                equipment.getDeposit(),
//                startDate,
//                endDate,
//                daysCount,
//                rentalCost,
//                totalCost
//        );
//        model.addAttribute("orderDetails", orderDetails);   // добавляем данные в модель
//        return "order-create";      // возвращаем html шаблон
//    }
//
//    @PostMapping("/create")
//    public String createOrder(@Valid CreateOrderDto orderModel, // получаем данные из формы согласно аннотациям в классе DTO
//                               Principal principal,
//                               RedirectAttributes redirectAttributes) {
//        log.debug("Обработка POST запроса на добавление заказа");
//        String username = principal.getName();              // получаем текущего пользователя
//        User user = userService.getUser(username);          // находим пользователя по логину
//        orderService.createOrder(orderModel, user.getId()); // передаем бизнес-логику создания заказа в сервисный слой
//        redirectAttributes.addFlashAttribute("successMessage", "Заказ успешно добавлен!");
//        return "order-done";                                // возвращаем html шаблон
//    }
//
//
//    @GetMapping("/my")
//    @Transactional(readOnly = true)
//    public String showUserOrders(Principal principal, Model model) {
//        log.debug("Отображение заказов текущего пользователя");
//        String username = principal.getName();
//        User user = userService.getUser(username);
//        List<ShowOrderInfoDto> equipments = orderService.getUserOrders(user.getId());
//        model.addAttribute("orders", equipments);
//        return "order-my";
//    }
//
//    @GetMapping("/details/{orderId}")
//    public String showOrderDetails(@PathVariable String orderId, Model model) {
//        log.debug("Отображение деталей заказа: {}", orderId);
//        ShowDetailedOrderInfoDto orderDetails = orderService.getOrderDetails(orderId);
//        ShowEquipmentInfoDto equipment = equipmentService.getEquipment(orderDetails.getEquipmentId());
//        model.addAttribute("equipment", equipment);
//        model.addAttribute("order", orderDetails);
//        return "order-details";
//    }
//
//    @PostMapping("/cancel/{orderId}")
//    public String cancelOrder(@PathVariable String orderId, RedirectAttributes redirectAttributes) {
//        log.debug("Отмена заказа: {}", orderId);
//        try {
//            orderService.cancelOrder(orderId);
//            redirectAttributes.addFlashAttribute("orderCancelledSuccess", true);
//            redirectAttributes.addFlashAttribute("successMessage", "Заказ успешно отменен!");
//        } catch (Exception e) {
//            log.warn("Ошибка отмены заказа: {}", e.getMessage());
//            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
//        }
//        return "redirect:/orders/my";
//    }
//
//    @PostMapping("/delete/{orderId}")
//    public String deleteOrder(@PathVariable String orderId, RedirectAttributes redirectAttributes) {
//        log.debug("удаление заказа: {}", orderId);
//        try {
//            orderService.cancelOrder(orderId);
//            redirectAttributes.addFlashAttribute("orderCancelledSuccess", true);
//            redirectAttributes.addFlashAttribute("successMessage", "Заказ успешно удален!");
//        } catch (Exception e) {
//            log.warn("Ошибка отмены заказа: {}", e.getMessage());
//            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
//        }
//        return "redirect:/orders/all";
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<Page<ShowOrderInfoDto>>> showAllOrders(@RequestParam(defaultValue = "0") int page,
//                                                                      @RequestParam(defaultValue = "10") int size,
//                                                                      Model model) {
//        List<Page<ShowOrderInfoDto>> orders;
//        log.debug("Отображение всех заказов (админ/модератор)");
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//        orders = Collections.singletonList(orderService.getAllOrders(pageable));
//
//        return ResponseEntity.ok(orders);
//    }
//
//    @GetMapping("/calculate")
//    @ResponseBody
//    public Double calculateOrderTotal(@RequestParam String equipmentId,
//                                      @RequestParam LocalDate startDate,
//                                      @RequestParam LocalDate endDate) {
//        log.debug("Расчет стоимости заказа для оборудования: {}", equipmentId);
//        try {
//            return orderService.calculateOrderTotal(equipmentId, startDate, endDate);
//        } catch (Exception e) {
//            log.warn("Ошибка расчета стоимости: {}", e.getMessage());
//            return 0.0;
//        }
//    }
//}