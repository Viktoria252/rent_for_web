package com.example.rent.web;

import com.example.rent.dto.AddEquipmentDto;
import com.example.rent.dto.ShowDetailedEquipmentInfoDto;
import com.example.rent.dto.ShowEquipmentInfoDto;
import com.example.rent.services.EquipmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;




@Slf4j
@RestController
@RequestMapping("/api/equipments")
public class EquipmentController {
    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
        log.info("EquipmentController инициализирован");
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShowEquipmentInfoDto>> showAllEquipments() {
        log.debug("Получение всех товаров");
        List<ShowEquipmentInfoDto> equipments = equipmentService.allEquipments();
        return ResponseEntity.ok(equipments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowDetailedEquipmentInfoDto> getEquipmentById(@PathVariable String id) {
        log.debug("Получение товара по ID: {}", id);
        ShowDetailedEquipmentInfoDto equipment = equipmentService.equipmentDetailsById(id);
        return ResponseEntity.ok(equipment);
    }

    // Если нужно добавить товар (опционально)
    @PostMapping("/add")
    public ResponseEntity<ShowEquipmentInfoDto> addEquipment(@RequestBody AddEquipmentDto equipmentModel) {
        log.debug("Добавление нового товара");
        ShowEquipmentInfoDto newEquipment = equipmentService.addEquipment(equipmentModel);
        return ResponseEntity.ok(newEquipment);
    }
}

//@Slf4j
//@Controller
//@RequestMapping("/equipments")
//public class EquipmentController {
//    private final EquipmentService equipmentService;
//
//    public EquipmentController(EquipmentService equipmentService) {
//        this.equipmentService = equipmentService;
//        log.info("EquipmentController инициализирован");
//    }
//
////    @GetMapping("/add")
////    public String addEquipment() {
////        log.debug("Отображение формы добавления товара");
////        return "equipment-add";
////    }
//
//    @PostMapping("/add")
//    public ResponseEntity<ShowEquipmentInfoDto> addEquipment(@Valid AddEquipmentDto equipmentModel) {
//        log.debug("Обработка POST запроса на добавление компании");
//        ShowEquipmentInfoDto addEquipment = equipmentService.addEquipment(equipmentModel);
//        return ResponseEntity.status(HttpStatus.CREATED).body(addEquipment);
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<ShowEquipmentInfoDto>> showAllEquipments(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "9") int size,
//            @RequestParam(defaultValue = "title") String sortBy,
//            @RequestParam(required = false) String search) {
//
//        log.debug("Отображение списка оборудования: страница={}, размер={}, сортировка={}, поиск={}",
//                page, size, sortBy, search);
//        List<ShowEquipmentInfoDto> equipments;
//
//        if (search != null && !search.trim().isEmpty()) {
//            equipments = equipmentService.searchByTitle(search);
//        } else {
//            equipments = equipmentService.allEquipments();
//        }
//
//        return ResponseEntity.ok(equipments);
//    }

//    @GetMapping("/equipment-details/{equipment-title}")
//    public String equipmentDetails(@PathVariable("equipment-title") String equipmentTitle, Model model) {
//        log.debug("Запрос деталей оборудования: {}", equipmentTitle);
//        model.addAttribute("equipmentDetails", equipmentService.equipmentDetails(equipmentTitle));
//        return "equipment-details";
//    }
//
//    @GetMapping("/equipment-delete/{equipment-title}")
//    public String deleteEquipment(@PathVariable("equipment-title") String equipmentTitle,
//                                  RedirectAttributes redirectAttributes) {
//        log.debug("Запрос на удаление товара: {}", equipmentTitle);
//        equipmentService.removeEquipment(equipmentTitle);
//        redirectAttributes.addFlashAttribute("successMessage",
//                "Товар '" + equipmentTitle + "' успешно удален!");
//        return "redirect:/equipments/all";
//    }

//    @GetMapping("/category/{category}")
//    public String findByCategory(@PathVariable String category, Model model) {
//        log.debug("Фильтрация оборудования по категории: {}", category);
//        List<ShowEquipmentInfoDto> equipmentList = equipmentService.findByCategory(category);
//        model.addAttribute("equipmentInfos", equipmentList);
//        model.addAttribute("filterType", "category");
//        model.addAttribute("filterValue", category);
//        model.addAttribute("totalItems", equipmentList.size());
//        log.info("Найдено оборудования в категории '{}': {}", category, equipmentList.size());
//        return "equipment-all";
//    }
//
//    @GetMapping("/brand/{brand}")
//    public String findByBrand(@PathVariable String brand, Model model) {
//        log.debug("Фильтрация оборудования по бренду: {}", brand);
//        List<ShowEquipmentInfoDto> equipmentList = equipmentService.findByBrand(brand);
//        model.addAttribute("equipmentInfos", equipmentList);
//        model.addAttribute("filterType", "brand");
//        model.addAttribute("filterValue", brand);
//        model.addAttribute("totalItems", equipmentList.size());
//        log.info("Найдено оборудования бренда '{}': {}", brand, equipmentList.size());
//        return "equipment-all";
//    }
//
//    @GetMapping("/price/{minPrice}")
//    public String findByPrice(@PathVariable Double minPrice, Model model) {
//        log.debug("Фильтрация оборудования по минимальной цене: {}", minPrice);
//        List<ShowEquipmentInfoDto> equipmentList = equipmentService.findByDailyPriceGreaterThanOrderByDailyPriceAsc(minPrice);
//        model.addAttribute("equipmentInfos", equipmentList);
//        model.addAttribute("filterType", "price");
//        model.addAttribute("filterValue", minPrice);
//        model.addAttribute("totalItems", equipmentList.size());
//        log.info("Найдено оборудования с ценой от {}: {}", minPrice, equipmentList.size());
//        return "equipment-all";
//    }
//
//    @GetMapping("/deposit/{minDeposit}")
//    public String findByDeposit(@PathVariable Double minDeposit, Model model) {
//        log.debug("Фильтрация оборудования по минимальному депозиту: {}", minDeposit);
//        List<ShowEquipmentInfoDto> equipmentList = equipmentService.findByDepositGreaterThanOrderByDepositAsc(minDeposit);
//        model.addAttribute("equipmentInfos", equipmentList);
//        model.addAttribute("filterType", "deposit");
//        model.addAttribute("filterValue", minDeposit);
//        model.addAttribute("totalItems", equipmentList.size());
//        log.info("Найдено оборудования с депозитом от {}: {}", minDeposit, equipmentList.size());
//        return "equipment-all";
//    }
//
//    @GetMapping("/top-popular")
//    public String topPopular(Model model) {
//        log.debug("Запрос топ-3 популярного оборудования");
//        List<ShowEquipmentInfoDto> topEquipment = equipmentService.top3ByRental_count();
//        model.addAttribute("equipmentInfos", topEquipment);
//        model.addAttribute("filterType", "top-popular");
//        model.addAttribute("totalItems", topEquipment.size());
//        log.info("Найдено топ оборудования: {}", topEquipment.size());
//        return "equipment-all";
//    }

//    @GetMapping("/reset")
//    public String resetFilters() {
//        log.debug("Сброс фильтров оборудования");
//        return "redirect:/equipments/all";
//    }
//}