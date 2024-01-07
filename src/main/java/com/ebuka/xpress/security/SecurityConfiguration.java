package com.ebuka.xpress.security;

import com.ebuka.xpress.config.ApplicationAuthenticationEntryPoint;
import com.ebuka.xpress.service.ApplicationUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class responsible for setting up security features for the application.
 * Configures authentication, authorization, filters, and security-related settings.
 *
 * @author ChukwuEbuka
 * @description Configures security settings for the application.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final XpressJwtAuthFilter xpressJwtAuthFilter;
    private final ApplicationAuthenticationEntryPoint authenticationEntryPoint;
    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final UserDetailService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    // Define allowed endpoints (commented out for reference)
    // private final String[] allowedEndpoints = new String[]{...};

    /**
     * Configures the authentication manager bean.
     * Sets up the DaoAuthenticationProvider with the userDetailsService and passwordEncoder.
     *
     * @return AuthenticationProvider instance using DaoAuthenticationProvider
     * @throws Exception Exception if an error occurs while configuring the authentication manager
     */
    @Bean
    public AuthenticationProvider authenticationManager() throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    /**
     * Configures the security filter chain for HTTP security configurations.
     * Sets up security settings, exception handling, session management, authorization rules, and filters.
     *
     * @param httpSecurity HttpSecurity instance for configuring security
     * @return SecurityFilterChain instance representing the configured security filter chain
     * @throws Exception Exception if an error occurs while configuring the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizationManagerRequestMatcherRegistry) ->
                        authorizationManagerRequestMatcherRegistry.requestMatchers("/api/v1/users/signup", "/api/v1/users/login").permitAll()
                                .anyRequest()
                                .authenticated())
                .authenticationProvider(authenticationManager())
                .addFilterBefore(xpressJwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(LogoutConfigurer::permitAll);
        return httpSecurity.build();
    }
}

