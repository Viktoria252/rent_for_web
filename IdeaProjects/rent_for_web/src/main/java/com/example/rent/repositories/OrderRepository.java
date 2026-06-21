package com.example.rent.repositories;

import com.example.rent.models.entities.Order;
import com.example.rent.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Transactional(readOnly = true)
    List<Order> findByUserId(String userId);


    @Modifying
    @Transactional
    void deleteById(String id);


    @Transactional
    Page<Order> findAll(Pageable pageable);
}
