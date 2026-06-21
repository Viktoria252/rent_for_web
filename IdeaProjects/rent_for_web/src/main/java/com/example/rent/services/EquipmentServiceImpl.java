package com.example.rent.services;

import com.example.rent.dto.AddEquipmentDto;
import com.example.rent.dto.ShowDetailedEquipmentInfoDto;
import com.example.rent.dto.ShowEquipmentInfoDto;
import com.example.rent.models.entities.Equipment;
import com.example.rent.models.exceptions.EquipmentNotFoundException;
import com.example.rent.repositories.EquipmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class EquipmentServiceImpl implements EquipmentService{
    private final EquipmentRepository equipmentRepository;
    private final ModelMapper mapper;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository, ModelMapper mapper) {
        this.equipmentRepository = equipmentRepository;
        this.mapper = mapper;
        log.info("EquipmentServiceImpl инициализирован");
    }

    @Override
    public ShowEquipmentInfoDto getEquipment(String id) {
        log.debug("Поиск оборудования по ID: {}", id);
        Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> {
            log.warn("Оборудование не найдено: {}", id);
            return new EquipmentNotFoundException("Оборудование с именем '" + id + "' не найдено");
        });
        return mapper.map(equipment, ShowEquipmentInfoDto.class);
    }

    @Override
    public ShowDetailedEquipmentInfoDto equipmentDetailsById(String id) {
        log.debug("Получение деталей оборудования по ID: {}", id);
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException("Оборудование не найдено: " + id));
        return mapper.map(equipment, ShowDetailedEquipmentInfoDto.class);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "equipments", allEntries = true)
    public ShowEquipmentInfoDto addEquipment(AddEquipmentDto equipmentDTO) {
        log.debug("Добавление нового оборудования: {}", equipmentDTO.getTitle());
        Equipment equipment = mapper.map(equipmentDTO, Equipment.class);
        equipmentRepository.save(equipment);
        log.info("Оборудование успешно добавлено: {}", equipment.getTitle());
        return null;
    }

    @Override
    public Equipment findById(String id) {
        log.debug("Поиск оборудования по ID: {}", id);
        return equipmentRepository.findById(id).get(); // обращаемся к репозиторию и находим оборудование по id
    }

    @Override
    @Cacheable(value = "equipments", key = "'all'")
    public List<ShowEquipmentInfoDto> allEquipments() {
        log.debug("Получение списка всех оборудований");
        List<ShowEquipmentInfoDto> equipments = equipmentRepository.findAll().stream()
                .map(equipment -> mapper.map(equipment, ShowEquipmentInfoDto.class))
                .collect(Collectors.toList());
        log.info("Найдено оборудования: {}", equipments.size());
        return equipments;
    }

    @Override
    public List<ShowEquipmentInfoDto> searchByTitle(String searchTerm) {
        log.debug("Поиск оборудования по названию: {}", searchTerm);
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return List.of();
        }
        return equipmentRepository.searchByTitle(searchTerm).stream()
                .map(equipment -> mapper.map(equipment, ShowEquipmentInfoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ShowEquipmentInfoDto> allEquipmentsPaginated(Pageable pageable) {
        log.debug("Получение оборудования с пагинацией: страница {}, размер {}",
                pageable.getPageNumber(), pageable.getPageSize());
        return equipmentRepository.findAll(pageable)
                .map(company -> mapper.map(company, ShowEquipmentInfoDto.class));
    }

    @Override
    public List<ShowEquipmentInfoDto> findByCategory(String category) {
        log.debug("Поиск оборудования по категории: {}", category);
        return equipmentRepository.findByCategory(category).stream()
                .map(equipment -> mapper.map(equipment, ShowEquipmentInfoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowEquipmentInfoDto> findByBrand(String brand) {
        log.debug("Поиск оборудования по бренду: {}", brand);
        return equipmentRepository.findByBrand(brand).stream()
                .map(equipment -> mapper.map(equipment, ShowEquipmentInfoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowEquipmentInfoDto> findByDailyPriceGreaterThanOrderByDailyPriceAsc(Double minDailyPrice) {
        log.debug("Поиск оборудования по цене, отсортированные в порядке возрастания: {}", minDailyPrice);
        return equipmentRepository.findByDailyPriceGreaterThanOrderByDailyPriceAsc(minDailyPrice).stream()
                .map(equipment -> mapper.map(equipment, ShowEquipmentInfoDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<ShowEquipmentInfoDto> findByDepositGreaterThanOrderByDepositAsc(Double minDeposit) {
        log.debug("Поиск оборудования по депозиту, отсортированные в порядке возрастания: {}", minDeposit);
        return equipmentRepository.findByDepositGreaterThanOrderByDepositAsc(minDeposit).stream()
                .map(equipment -> mapper.map(equipment, ShowEquipmentInfoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowEquipmentInfoDto> top3ByRental_count() {
        log.debug("Топ 3 оборудования по использованию: ");
        return equipmentRepository.top3ByRental_count().stream()
                .map(equipment -> mapper.map(equipment, ShowEquipmentInfoDto.class))
                .collect(Collectors.toList());
    }
    @Override
    @Cacheable(value = "equipment", key = "#equipmentTitle", unless = "#result == null")
    public ShowDetailedEquipmentInfoDto equipmentDetails(String equipmentTitle) {
        log.debug("Получение деталей оборудования: {}", equipmentTitle);
        Equipment equipment = equipmentRepository.findByTitle(equipmentTitle)
                .orElseThrow(() -> {
                    log.warn("Оборудование не найдено: {}", equipmentTitle);
                    return new EquipmentNotFoundException("Оборудование с именем '" + equipmentTitle + "' не найдено");
                });
        return mapper.map(equipment, ShowDetailedEquipmentInfoDto.class);
    }

    @Override
    public void removeEquipment(String equipmentTitle) {
        log.debug("Удаление оборудования: {}", equipmentTitle);
        if (!equipmentRepository.existsByTitle(equipmentTitle)) {
            log.warn("Попытка удалить несуществующее оборудование: {}", equipmentTitle);
            throw new EquipmentNotFoundException("Оборудования с именем '" + equipmentTitle + "' не найдена");
        }
        equipmentRepository.deleteByTitle(equipmentTitle);
        log.info("Компания успешно удалена: {}", equipmentTitle);
    }
}
