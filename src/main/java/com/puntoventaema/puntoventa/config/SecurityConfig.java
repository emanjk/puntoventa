package com.puntoventaema.puntoventa.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.SecurityFilterChain;

//Configuración relacionada a Spring Security

@Configuration
@EnableWebSecurity //habilitar la seguridad web.
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Desactiva la protección CSRF
                .authorizeRequests(auth -> auth
                        .anyRequest().permitAll() // Permitir el acceso a todos los usuarios, sin autenticación ni autorización
                );
        return http.build();
    }

}
