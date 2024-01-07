package com.ebuka.xpress.service.impl;

import com.ebuka.xpress.model.entity.User;
import com.ebuka.xpress.repository.UserRepository;
import com.ebuka.xpress.service.ApplicationUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsServiceImpl implements ApplicationUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User resolveUserByEmailAddress(String emailAddress) {
        Optional<User> foundUser;
        if (!Objects.isNull(emailAddress)) {
            foundUser = userRepository.findUserByEmail(emailAddress);
            if (foundUser.isEmpty()) {
                throw new UsernameNotFoundException("user not found");
            } else {
                return foundUser.get();
            }
        }
        throw new UsernameNotFoundException("user not found");
    }

    @Override
    public User resolveUserById(Long userId) {
        Optional<User> foundUser;
        if (!Objects.isNull(userId)) {
            foundUser = userRepository.findUserById(userId);
            if (foundUser.isEmpty()) {
                throw new UsernameNotFoundException("user not found");
            } else {
                return foundUser.get();
            }
        }
        throw new UsernameNotFoundException("user not found");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> optionalUser = userRepository.findUserByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }
        var foundUser = optionalUser.get();
        if (!foundUser.isActive() || foundUser.isBlocked()) {
            throw new UsernameNotFoundException("user account is not enabled or blocked");
        }

        return org.springframework.security.core.userdetails.User.withUsername(foundUser.getEmail())
                .password(foundUser.getPassword()).accountExpired(false).accountLocked(false).credentialsExpired(false)
                .disabled(false).authorities(foundUser.getUserType().toString()).build();
    }
}
