package com.example.rent.repositories;

import com.example.rent.models.entities.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, String> {

    Optional<Equipment> findByTitle(String title);
    Optional<Equipment> findById(String id);
    boolean existsByTitle(String title);

    List<Equipment> findByCategory(String category);

    List<Equipment> findByBrand(String brand);
    @Query("SELECT e FROM Equipment e WHERE " +
            "LOWER(e.title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Equipment> searchByTitle(@Param("searchTerm") String searchTerm);
    List<Equipment> findByDailyPriceGreaterThanOrderByDailyPriceAsc(Double minDailyPrice);
    List<Equipment> findByDepositGreaterThanOrderByDepositAsc(Double minDeposit);
    List<Equipment> findByCategoryAndBrand(String category, String brand);

    @Query("SELECT e FROM Equipment e ORDER BY e.rentalCount DESC LIMIT 3")
    List<Equipment> top3ByRental_count();

    @Modifying
    @Transactional
    void deleteByTitle(String title);
}

