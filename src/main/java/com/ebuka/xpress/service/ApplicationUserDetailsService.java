package com.ebuka.xpress.service;

import com.ebuka.xpress.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ApplicationUserDetailsService extends UserDetailsService {

    User resolveUserByEmailAddress(String emailAddress);
    User resolveUserById(Long userId);
}
