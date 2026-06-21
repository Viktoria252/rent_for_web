package com.example.rent;

import com.example.rent.models.entities.*;
import com.example.rent.models.enums.UserRoles;
import com.example.rent.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Инициализация начальных данных при запуске приложения.
 */
@Slf4j
@Component
public class Init implements CommandLineRunner {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final EquipmentRepository equipmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultPassword;

    public Init(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           EquipmentRepository equipmentRepository,
                           PasswordEncoder passwordEncoder,
                           @Value("${app.default.password:topsecret}") String defaultPassword) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.equipmentRepository = equipmentRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultPassword = defaultPassword;
        log.info("DataInitializer компонент инициализирован");
    }

    @Override
    public void run(String... args) {
        log.info("Запуск инициализации начальных данных");
        initRoles();
        initUsers();
        initEquipments();
        log.info("Инициализация начальных данных завершена");
    }

    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            log.info("Создание базовых ролей...");
            userRoleRepository.saveAll(List.of(
                    new Role(UserRoles.ADMIN),
                    new Role(UserRoles.MODERATOR),
                    new Role(UserRoles.USER)
            ));
            log.info("Роли созданы: ADMIN, MODERATOR, USER");
        } else {
            log.debug("Роли уже существуют, пропуск инициализации");
        }
    }

    private void initUsers() {
        if (userRepository.count() == 0) {
            log.info("Создание пользователей по умолчанию...");
            initAdmin();
            initModerator();
            initNormalUsers();
            log.info("Пользователи по умолчанию созданы");
        } else {
            log.debug("Пользователи уже существуют, пропуск инициализации");
        }
    }

    private void initAdmin() {
        var adminRole = userRoleRepository
                .findRoleByName(UserRoles.ADMIN)
                .orElseThrow();

        var adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode(defaultPassword));
        adminUser.setFullName("Администратор Системы");
        adminUser.setBudget(1000000.0);
        adminUser.setRoles(List.of(adminRole));

        userRepository.save(adminUser);
        log.info("Создан администратор: admin@berry.ru");
    }

    private void initModerator() {
        var moderatorRole = userRoleRepository
                .findRoleByName(UserRoles.MODERATOR)
                .orElseThrow();

        var moderatorUser = new User();
        moderatorUser.setUsername("moderator");
        moderatorUser.setPassword(passwordEncoder.encode(defaultPassword));
        moderatorUser.setFullName("Модератор Оборудования");
        moderatorUser.setBudget(500000.0);
        moderatorUser.setRoles(List.of(moderatorRole));

        userRepository.save(moderatorUser);
        log.info("Создан модератор: moderator@berry.ru");
    }

    private void initNormalUsers() {
        var userRole = userRoleRepository
                .findRoleByName(UserRoles.USER)
                .orElseThrow();

        // Пользователь 1
        var user1 = new User();
        user1.setUsername("alex.photo");
        user1.setPassword(passwordEncoder.encode("password123"));
        user1.setFullName("Александр Фотографов");
        user1.setBudget(250000.0);
        user1.setRoles(List.of(userRole));
        userRepository.save(user1);

        // Пользователь 2
        var user2 = new User();
        user2.setUsername("marina.video");
        user2.setPassword(passwordEncoder.encode("password123"));
        user2.setFullName("Марина Видеографова");
        user2.setBudget(300000.0);
        user2.setRoles(List.of(userRole));
        userRepository.save(user2);

        // Пользователь 3
        var user3 = new User();
        user3.setUsername("studio.pro");
        user3.setPassword(passwordEncoder.encode("password123"));
        user3.setFullName("Студия Профи");
        user3.setBudget(1500000.0);
        user3.setRoles(List.of(userRole));
        userRepository.save(user3);

        var demoUser = new User();
        demoUser.setUsername("demo_user");
        demoUser.setPassword(passwordEncoder.encode("demo123"));
        demoUser.setFullName("Демо-пользователь");
        demoUser.setBudget(9999999.0); // огромный бюджет для тестов
        demoUser.setRoles(List.of(userRole));
        userRepository.save(demoUser);

        log.info("Созданы обычные пользователи: 4 пользователя");
    }

    private void initEquipments() {
        if (equipmentRepository.count() == 0) {
            log.info("Создание начального оборудования...");

            // Фотоаппараты
            Equipment camera1 = new Equipment();
            camera1.setTitle("Canon EOS R5");
            camera1.setDescription("Профессиональная беззеркальная камера с разрешением 45 Мп и записью видео 8K");
            camera1.setCategory("Фотоаппараты");
            camera1.setBrand("Canon");
            camera1.setDailyPrice(4500.0);
            camera1.setDeposit(150000.0);
            camera1.setRentalCount(0);
            camera1.setImage("https://www.rentaphoto.ru/upload/iblock/ac2/mra6a04mpptq2byuvoy9ztpzd3wj030x/9e5a2d60-b888-11eb-4e9b-78542e6ffaa2_50359fb4-ba29-11eb-4e9b-78542e6ffaa2.jpg");
            equipmentRepository.save(camera1);

            Equipment camera2 = new Equipment();
            camera2.setTitle("Sony A7 IV");
            camera2.setDescription("Полнокадровая беззеркальная камера с улучшенной автофокусировкой");
            camera2.setCategory("Фотоаппараты");
            camera2.setBrand("Sony");
            camera2.setDailyPrice(3800.0);
            camera2.setDeposit(120000.0);
            camera2.setRentalCount(0);
            camera2.setImage("https://photopark.ru/uploads/cache/Items/Item5167/96981c1ed4-7.png");
            equipmentRepository.save(camera2);

            Equipment camera3 = new Equipment();
            camera3.setTitle("Nikon Z6 II");
            camera3.setDescription("Надежная полнокадровая камера для профессиональной съемки");
            camera3.setCategory("Фотоаппараты");
            camera3.setBrand("Nikon");
            camera3.setDailyPrice(3200.0);
            camera3.setDeposit(100000.0);
            camera3.setRentalCount(0);
            camera3.setImage("https://www.yarkiy.ru/system/uploads/preview/photo_storage/227815/1602636707_1597167.jpg");
            equipmentRepository.save(camera3);

            // Объективы
            Equipment lens1 = new Equipment();
            lens1.setTitle("Canon RF 24-70mm f 2.8");
            lens1.setDescription("Универсальный зум-объектив для профессиональной съемки");
            lens1.setCategory("Объективы");
            lens1.setBrand("Canon");
            lens1.setDailyPrice(1500.0);
            lens1.setDeposit(80000.0);
            lens1.setRentalCount(0);
            lens1.setImage("https://www.rentaphoto.ru/upload/iblock/86f/mbw9v5zy50ir6lj7ckcneka5crylstlo/f60e1b6d-9895-11ec-882f-2c4d54583ea2_f60e1b6e-9895-11ec-882f-2c4d54583ea2.jpg");
            equipmentRepository.save(lens1);

            Equipment lens2 = new Equipment();
            lens2.setTitle("Sony FE 70-200mm f 2.8");
            lens2.setDescription("Телеобъектив для спортивной и портретной съемки");
            lens2.setCategory("Объективы");
            lens2.setBrand("Sony");
            lens2.setDailyPrice(1800.0);
            lens2.setDeposit(90000.0);
            lens2.setRentalCount(0);
            lens2.setImage("https://www.sony.ru/image/d2a57c9585b7f853c33af0189beb3ee3?fmt=pjpeg&wid=330&bgcolor=FFFFFF&bgc=FFFFFF");
            equipmentRepository.save(lens2);

            // Видеокамеры
            Equipment video1 = new Equipment();
            video1.setTitle("Sony FX3");
            video1.setDescription("Кинематографическая камера для профессионального видео");
            video1.setCategory("Видеокамеры");
            video1.setBrand("Sony");
            video1.setDailyPrice(5200.0);
            video1.setDeposit(180000.0);
            video1.setRentalCount(0);
            video1.setImage("https://www.rentaphoto.ru/upload/iblock/868/b1ygq14xb5nmroh7dpz9oybcnc4p9j3a/42e391c5-d031-11eb-8818-2c4d54583ea2_42e391c6-d031-11eb-8818-2c4d54583ea2.jpg");
            equipmentRepository.save(video1);

            Equipment video2 = new Equipment();
            video2.setTitle("Blackmagic Pocket Cinema 6K");
            video2.setDescription("Компактная кинокамера с RAW записью");
            video2.setCategory("Видеокамеры");
            video2.setBrand("Blackmagic");
            video2.setDailyPrice(4800.0);
            video2.setDeposit(160000.0);
            video2.setRentalCount(0);
            video2.setImage("https://www.rentaphoto.ru/upload/iblock/09e/s8xfw553jnnmtbizp7lop50lz4dsspfb/85eb856c-b888-11eb-4e9b-78542e6ffaa2_abb4037b-70bb-11ed-bb22-887be834b9a2.jpg");
            equipmentRepository.save(video2);

            // Осветительное оборудование
            Equipment light1 = new Equipment();
            light1.setTitle("Aputure 300D");
            light1.setDescription("Мощный светодиодный осветитель с регулируемой цветовой температурой");
            light1.setCategory("Свет");
            light1.setBrand("Aputure");
            light1.setDailyPrice(1200.0);
            light1.setDeposit(40000.0);
            light1.setRentalCount(0);
            light1.setImage("https://www.rentaphoto.ru/upload/iblock/246/8jog1irf2c73rfnthjpne06lspuijice/56774157b3.jpg");
            equipmentRepository.save(light1);

            Equipment light2 = new Equipment();
            light2.setTitle("Godox SL-60W");
            light2.setDescription("Компактный светодиодный осветитель для студийной съемки");
            light2.setCategory("Свет");
            light2.setBrand("Godox");
            light2.setDailyPrice(800.0);
            light2.setDeposit(25000.0);
            light2.setRentalCount(0);
            light2.setImage("https://magprofoto.ru/19054-large_default/osvetitel-svetodiodnyj-godox-sl60w-studijnyj.jpg");
            equipmentRepository.save(light2);

            // Аудио оборудование
            Equipment audio1 = new Equipment();
            audio1.setTitle("Rode NTG3");
            audio1.setDescription("Профессиональный направленный микрофон для видеосъемки");
            audio1.setCategory("Аудио");
            audio1.setBrand("Rode");
            audio1.setDailyPrice(600.0);
            audio1.setDeposit(20000.0);
            audio1.setRentalCount(0);
            audio1.setImage("https://pixel24.ru/page_images/images/3(14).jpg");
            equipmentRepository.save(audio1);

            Equipment audio2 = new Equipment();
            audio2.setTitle("Zoom H6");
            audio2.setDescription("Портативный рекордер с 6 входами для профессиональной записи");
            audio2.setCategory("Аудио");
            audio2.setBrand("Zoom");
            audio2.setDailyPrice(900.0);
            audio2.setDeposit(30000.0);
            audio2.setRentalCount(0);
            audio2.setImage("https://zoomrussia.ru/wp-content/uploads/2025/03/h6ab_listimage_1.original.png");
            equipmentRepository.save(audio2);

            log.info("Создано оборудование: 11 позиций в разных категориях");
        } else {
            log.debug("Оборудование уже существует, пропуск инициализации");
        }
    }
}
