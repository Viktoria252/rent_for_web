package com.example.rent.config;

import com.example.rent.models.enums.UserRoles;
import com.example.rent.repositories.UserRepository;
import com.example.rent.services.AppUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Slf4j
@Configuration
public class AppSecurityConfiguration {

    private final UserRepository userRepository;

    public AppSecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
        log.info("AppSecurityConfiguration инициализирована");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Настройка SecurityFilterChain");

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.disable())
                .logout(logout -> logout.disable())
                .httpBasic(httpBasic -> httpBasic.disable());
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                        // Публичные страницы
//                        .requestMatchers("/", "/users/login", "/users/register", "/users/login-error",
//                                "/equipments/all", "/equipments/category/**", "/equipments/price/**", "/equipments/reset",
//                                "/equipments/deposit/**", "/equipments/brand/**", "/equipments/top-popular", "/equipments/equipment-details/**").permitAll()
//                        // Админские routes
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        // Модераторские routes
//                        .requestMatchers( "/orders/all")
//                        .hasAnyRole("ADMIN", "MODERATOR")
//                        // Все остальные требуют аутентификации
//                        .anyRequest().authenticated()
//                )
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/users/login")
//                        .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
//                        .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
//                        .defaultSuccessUrl("/", true)
//                        .failureForwardUrl("/users/login-error")
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .permitAll()
//                )
//                .exceptionHandling(exception -> exception
//                        .accessDeniedPage("/users/login")
//                );

        return http.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsService(userRepository);
    }
}