package com.ebuka.xpress.controller;

import com.ebuka.xpress.model.dto.base.BaseResponse;
import com.ebuka.xpress.model.dto.user.LoginRequest;
import com.ebuka.xpress.model.dto.user.SignUpRequest;
import com.ebuka.xpress.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
        * Controller handling user-related endpoints for registration and login.
        * Maps requests to '/api/v1/users' endpoints for user operations.
        *
        * @author ChukwuEbuka
        * @description Handles user registration and login endpoints.
        */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    /**
     * Endpoint for user registration (signup).
     *
     * @param signUpRequest SignUpRequest object containing user registration details
     * @return ResponseEntity containing the response to the registration request
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Validated SignUpRequest signUpRequest) {
        BaseResponse<?> br;
        br = userService.handleUserRegistration(signUpRequest);
        if (br.getResponseCode() == HttpStatus.OK.value()) {
            return ResponseEntity.ok().body(br);
        } else {
            return ResponseEntity.badRequest().body(br);
        }
    }

    /**
     * Endpoint for user login.
     *
     * @param loginRequest LoginRequest object containing user login details
     * @return ResponseEntity containing the response to the login request
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Validated LoginRequest loginRequest) {
        BaseResponse<?> br = userService.handleUserLogin(loginRequest);
        if (br.getResponseCode() == HttpStatus.OK.value()) {
            return ResponseEntity.ok().body(br);
        } else {
            return ResponseEntity.badRequest().body(br);
        }
    }
}
