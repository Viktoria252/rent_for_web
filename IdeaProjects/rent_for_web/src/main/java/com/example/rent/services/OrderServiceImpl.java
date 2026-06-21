package com.example.rent.services;

import com.example.rent.dto.CreateOrderDto;
import com.example.rent.dto.ShowDetailedOrderInfoDto;
import com.example.rent.dto.ShowEquipmentInfoDto;
import com.example.rent.dto.ShowOrderInfoDto;
import com.example.rent.models.entities.Equipment;
import com.example.rent.models.entities.Order;
import com.example.rent.models.entities.User;
import com.example.rent.models.exceptions.EquipmentNotFoundException;
import com.example.rent.repositories.EquipmentRepository;
import com.example.rent.repositories.OrderRepository;
import com.example.rent.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final EquipmentRepository equipmentRepository;

    private final UserService userService;
    private final ModelMapper mapper;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                            EquipmentRepository equipmentRepository, UserService userService, ModelMapper mapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.equipmentRepository = equipmentRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public List<ShowOrderInfoDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(equipment -> mapper.map(equipment, ShowOrderInfoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createOrder(CreateOrderDto orderDto, String userId) {
        log.debug("Создание заказа для пользователя: {}", userId);

        // Если userId не передан, берем демо-пользователя
        if (userId == null || userId.isEmpty()) {
            User demoUser = userRepository.findByUsername("demo_user")
                    .orElseThrow(() -> new IllegalArgumentException("Демо-пользователь не найден"));
            userId = demoUser.getId();
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        Equipment equipment = equipmentRepository.findById(orderDto.getEquipmentId())
                .orElseThrow(() -> new EquipmentNotFoundException("Оборудование не найдено"));

        long daysCount = ChronoUnit.DAYS.between(orderDto.getStartDate(), orderDto.getEndDate());
        Double rentalCost = equipment.getDailyPrice() * daysCount;
        Double totalCost = rentalCost + equipment.getDeposit();

        Order order = new Order();
        order.setUser(user);
        order.setEquipment(equipment);
        order.setStartDate(orderDto.getStartDate());
        order.setEndDate(orderDto.getEndDate());
        order.setTotalCost(totalCost);

        equipment.addRentalCount();
        equipmentRepository.save(equipment);
        orderRepository.save(order);

        log.info("Заказ создан с ID: {}", order.getId());
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "userOrders", allEntries = true),
            @CacheEvict(value = "allOrders", allEntries = true),
            @CacheEvict(value = "orderDetails", key = "#orderId")
    })
    public void cancelOrder(String orderId) {
        log.debug("Отмена заказа: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден"));

        orderRepository.deleteById(orderId);

        log.info("Заказ отменен: {}", orderId);
    }

    @Override
    @Cacheable(value = "orderDetails", key = "#orderId")
    public ShowDetailedOrderInfoDto getOrderDetails(String orderId) {
        log.debug("Получение деталей заказа: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден"));
        return mapper.map(order, ShowDetailedOrderInfoDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowOrderInfoDto> getUserOrders(String userId) {
        log.debug("Получение заказов пользователя: {}", userId);
        List<Order> orders = orderRepository.findByUserId(userId);
        // преобразование каждого Order в DTO
        return orders.stream()
                .map(order -> {
                    ShowOrderInfoDto dto = new ShowOrderInfoDto();
                    dto.setId(order.getId());
                    dto.setStartDate(order.getStartDate());
                    dto.setEndDate(order.getEndDate());
                    dto.setTotalCost(order.getTotalCost());
                    dto.setUserName(order.getUser().getUsername());
                    return dto;
                })
                .toList();
    }

    @Override
    public Page<ShowOrderInfoDto> getAllOrders(Pageable pageable) {
        log.debug("Получение всех заказов с пагинацией: страница {}, размер {}",
                pageable.getPageNumber(), pageable.getPageSize());
        return orderRepository.findAll(pageable)
                .map(order -> mapper.map(order, ShowOrderInfoDto.class));
    }

    @Override
    @Cacheable(value = "orderCalculations", key = "#equipmentIds.hashCode() + '_' + #startDate + '_' + #endDate")
    public Double calculateOrderTotal(String equipmentId, LocalDate startDate, LocalDate endDate) {
        log.debug("Расчет стоимости заказа для оборудования: {}", equipmentId);
        long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;

        Optional<Equipment> equipment = equipmentRepository.findById(equipmentId);

        return equipment.get().getDailyPrice() * days;
    }
}
