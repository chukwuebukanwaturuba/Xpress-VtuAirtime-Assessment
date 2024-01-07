package com.ebuka.xpress.config;

import com.ebuka.xpress.service.ApplicationUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;


/**
 * Configuration class for application settings and beans.
 *
 * @author ChukwuEbuka
 * @description Configures various beans required for application functionality.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final ApplicationUserDetailsService applicationUserDetailsService;

    /**
     * Provides a bean for the PasswordEncoder, using BCrypt hashing algorithm.
     *
     * @return PasswordEncoder instance using BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides a bean for the AuthenticationProvider using DaoAuthenticationProvider.
     * Configures userDetailsService and passwordEncoder.
     *
     * @return AuthenticationProvider instance using DaoAuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(applicationUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Provides a bean for RestTemplate used for making HTTP requests.
     *
     * @return RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * Provides a bean for ObjectMapper used for JSON serialization/deserialization.
     *
     * @return ObjectMapper instance
     */
    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
