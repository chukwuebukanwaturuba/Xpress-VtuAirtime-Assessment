package com.ebuka.xpress.service.impl;

import com.ebuka.xpress.builder.UserRecordBuilder;
import com.ebuka.xpress.security.XpressJwtService;
import com.ebuka.xpress.exception.InvalidCredentialsException;
import com.ebuka.xpress.model.dto.base.BaseResponse;
import com.ebuka.xpress.model.dto.user.AuthenticationToken;
import com.ebuka.xpress.model.dto.user.LoginRequest;
import com.ebuka.xpress.model.dto.user.SignUpRequest;
import com.ebuka.xpress.model.entity.User;
import com.ebuka.xpress.repository.UserRepository;
import com.ebuka.xpress.service.UserService;
import com.ebuka.xpress.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.Objects;

/**
 * Service implementation handling user-related operations such as registration and login.
 * Implements UserService interface.
 *
 * @author ChukwuEbuka
 * @description Handles user registration, login, and related operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationProvider authenticationProvider;
    private final UserRepository userRepository;
    private final PasswordUtils passwordUtils;
    private final XpressJwtService xpressJwtService;

    /**
     * Handles user registration based on SignUpRequest.
     *
     * @param signUpRequest SignUpRequest object containing user registration details
     * @return BaseResponse with the registration status and related information
     */
    @Override
    public BaseResponse<?> handleUserRegistration(final SignUpRequest signUpRequest) {
        BaseResponse<?> br;
        if (userExistsByEmailAddress(signUpRequest.getEmailAddress())) {
            throw new RuntimeException("User already Exists");
        } else {
            User user = UserRecordBuilder.mapSignupRequestToUser(signUpRequest);
            user.setPassword(passwordUtils.hashedPassword(signUpRequest.getPassword()));
            userRepository.save(user);
            br = new BaseResponse<>(HttpStatus.OK.value(), "user registered successfully", user);
        }
        return br;
    }

    /**
     * Handles user login based on LoginRequest.
     *
     * @param loginRequest LoginRequest object containing user login details
     * @return BaseResponse with the login status and related information
     */
    @Override
    public BaseResponse<?> handleUserLogin(final LoginRequest loginRequest) {
        try {
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(), loginRequest.getPassword()));
        } catch (Exception e) {
            throw new InvalidCredentialsException("wrong username or password");
        }
        User foundUser = userRepository.findUserByEmail(loginRequest.getEmailAddress()).get();
        auditLoginActivity(foundUser);
        AuthenticationToken authenticationToken = xpressJwtService.generateToken(foundUser);
        return new BaseResponse<>(HttpStatus.OK.value(), "user login successful", authenticationToken);
    }

    private boolean userExistsByEmailAddress(String emailAddress) {
        return userRepository.findUserByEmail(emailAddress).isPresent();
    }

    private void auditLoginActivity(User foundUser) {
        if (ObjectUtils.isEmpty(foundUser.getFirstLoginDate())) {
            foundUser.setFirstLoginDate(new Date());
        } else {
            foundUser.setLastLoginDate(new Date());
        }
        userRepository.save(foundUser);
    }
    /**
     * gets logged in based on token.
     *
     * @return user with userdetails
     */
    public User getUserFromSecurityContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(authentication)){
            return null;
        }
        Object principal = authentication.getPrincipal();
        if(!Objects.isNull(principal)){
            log.info("prinicipal =====> {}", principal);
            UserDetails userDetails = (UserDetails) principal;
            return userRepository.findUserByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("user does not exist"));

        }
        return null;
    }
}
