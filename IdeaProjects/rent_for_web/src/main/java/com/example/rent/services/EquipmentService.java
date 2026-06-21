package com.example.rent.services;

import com.example.rent.dto.AddEquipmentDto;
import com.example.rent.dto.ShowDetailedEquipmentInfoDto;
import com.example.rent.dto.ShowEquipmentInfoDto;
import com.example.rent.models.entities.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EquipmentService {
    ShowEquipmentInfoDto addEquipment(AddEquipmentDto equipmentDTO);
    Equipment findById(String id);
    ShowEquipmentInfoDto getEquipment(String id);
    ShowDetailedEquipmentInfoDto equipmentDetailsById(String id);

    List<ShowEquipmentInfoDto> allEquipments();

    Page<ShowEquipmentInfoDto> allEquipmentsPaginated(Pageable pageable);

    List<ShowEquipmentInfoDto> searchByTitle(String searchTerm);

    List<ShowEquipmentInfoDto> findByCategory(String category);

    List<ShowEquipmentInfoDto> findByBrand(String brand);

    List<ShowEquipmentInfoDto> findByDailyPriceGreaterThanOrderByDailyPriceAsc(Double minDailyPrice);

    List<ShowEquipmentInfoDto> findByDepositGreaterThanOrderByDepositAsc(Double minDeposit);

    List<ShowEquipmentInfoDto> top3ByRental_count();

    ShowDetailedEquipmentInfoDto equipmentDetails(String equipmentTitle);

    void removeEquipment(String equipmentTitle);

}
