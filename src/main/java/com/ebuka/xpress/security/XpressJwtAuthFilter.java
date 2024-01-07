package com.ebuka.xpress.security;

import com.ebuka.xpress.service.ApplicationUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/**
 * Filter responsible for handling JWT-based authentication for incoming requests.
 * Extends OncePerRequestFilter.
 *
 * @author ChukwuEbuka
 * @description Handles JWT-based authentication for incoming requests.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class XpressJwtAuthFilter extends OncePerRequestFilter {

    private final XpressJwtService xpressJwtService;
    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final UserDetailService userDetailService;

    /**
     * Performs the actual JWT-based authentication for incoming requests.
     *
     * @param request     HttpServletRequest object representing the incoming request
     * @param response    HttpServletResponse object representing the outgoing response
     * @param filterChain FilterChain for handling the request and response
     * @throws ServletException ServletException if a servlet-related exception occurs
     * @throws IOException      IOException if an input or output exception occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authenticationHeader = request.getHeader("Authorization");
        final String jwt;
        final String emailAddress;

        // Check for the existence and validity of the Authorization header
        if (ObjectUtils.isEmpty(authenticationHeader) || !authenticationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authenticationHeader.substring(7);
        emailAddress = xpressJwtService.extractUsername(jwt);

        // Validate the JWT and perform authentication
        if (!ObjectUtils.isEmpty(emailAddress) && ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userDetails = userDetailService.loadUserByUsername(emailAddress);
            if (!ObjectUtils.isEmpty(userDetails)) {
                if (xpressJwtService.isValidToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
