//package com.example.rent.web;
//
//import com.example.rent.dto.ShowEquipmentInfoDto;
//import com.example.rent.services.EquipmentService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
////Контроллер для главной страницы.
//@Slf4j
//@Controller
//public class HomeController {
//    private final EquipmentService equipmentService;
//
//    public HomeController(EquipmentService equipmentService) {
//        this.equipmentService = equipmentService;
//    }
//
//    @GetMapping("/")
//    public String homePage(Model model) {
//        log.debug("Отображение главной страницы");
//        log.debug("Запрос топ-3 популярного оборудования");
//        List<ShowEquipmentInfoDto> topEquipment = equipmentService.top3ByRental_count();
//        model.addAttribute("equipmentInfos", topEquipment);
//        return "index";
//    }
//}

