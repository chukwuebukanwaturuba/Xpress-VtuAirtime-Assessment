package com.ebuka.xpress.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom component serving as the entry point for authentication failures.
 * Implements AuthenticationEntryPoint interface.
 *
 * @author chukwu_ebuka.
 * @description Handles authentication failures by sending an unauthorized response.
 */
@Component
public class ApplicationAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Handles authentication failure by sending an unauthorized response.
     *
     * @param request        HttpServletRequest object representing the request
     * @param response       HttpServletResponse object representing the response
     * @param authException  AuthenticationException indicating the authentication failure
     * @throws IOException       IOException if an input or output exception occurs
     * @throws ServletException ServletException if a servlet-related exception occurs
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}