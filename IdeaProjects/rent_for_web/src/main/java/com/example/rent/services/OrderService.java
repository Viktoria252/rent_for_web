package com.example.rent.services;

import com.example.rent.dto.CreateOrderDto;
import com.example.rent.dto.ShowDetailedOrderInfoDto;
import com.example.rent.dto.ShowOrderInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    void createOrder(CreateOrderDto orderDto, String userId);
    void cancelOrder(String orderId);
    ShowDetailedOrderInfoDto getOrderDetails(String orderId);
    List<ShowOrderInfoDto> getUserOrders(String userId);
    List<ShowOrderInfoDto> getAllOrders();

    Page<ShowOrderInfoDto> getAllOrders(Pageable pageable);

    Double calculateOrderTotal(String equipmentId, LocalDate startDate, LocalDate endDate);
}
