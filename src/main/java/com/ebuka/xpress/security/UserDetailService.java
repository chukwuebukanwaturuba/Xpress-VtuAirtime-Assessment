package com.ebuka.xpress.security;

import com.ebuka.xpress.model.entity.User;
import com.ebuka.xpress.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Collections;

/**
 * Service responsible for retrieving user details for authentication purposes.
 * Implements Spring Security's UserDetailsService.
 *
 * @author ChukwuEbuka
 * @description Retrieves user details for authentication.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Retrieves user details based on the provided email for authentication.
     *
     * @param email The email used for user identification
     * @return UserDetails instance representing the user's details
     * @throws UsernameNotFoundException Exception thrown if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Retrieve user details from UserRepository based on email
        User appUser = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create user authorities based on user type
        Collection<SimpleGrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority(appUser.getUserType().name()));

        // Create UserDetails object using retrieved user details and authorities
        return new org.springframework.security.core.userdetails.User(
                appUser.getEmail(), appUser.getPassword(), authorities);
    }
}

