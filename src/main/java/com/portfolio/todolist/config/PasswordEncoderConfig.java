package com.portfolio.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuração separada para PasswordEncoder.
 * Separada do SecurityConfig para evitar dependências circulares.
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Bean para codificação de senhas usando BCrypt.
     * 
     * @return PasswordEncoder configurado
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
