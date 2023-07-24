package com.example.demo.config;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserService userService;

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/getAllUser", "/getUser", "/index", "/user").permitAll()
                        .requestMatchers("/add","/edit","/delete").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationConfigurer() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
//Класс WebSecurityConfig содержит аннотацию @EnableWebMvcSecurity для включения поддержки безопасности Spring Security и Spring MVC интеграцию.
// Он также расширяет WebSecurityConfigurerAdapter и переопределяет пару методов для установки некоторых настроек безопасности.
//Метод securityFilterChain(HttpSecurity) определяет, какие URL пути должны быть защищены, а какие нет. В частности, "/" и "/home" настроены без требования к авторизации.
// Ко всем остальным путям должна быть произведена аутентификация.
//Когда пользователь успешно войдет в систему, он будет перенаправлен на предыдущую запрашиваемую страницу, требующую авторизацию.
// Здесь вы определили собственную "/login" страницу в loginPage() и каждый имеет доступ к ней.
//Что касается метода configure(AuthenticationManagerBuilder), то он создает в памяти хранилище пользователей с единственным пользователем.
// Этому пользователю дано имя "user", пароль "password" и роль "ROLE".