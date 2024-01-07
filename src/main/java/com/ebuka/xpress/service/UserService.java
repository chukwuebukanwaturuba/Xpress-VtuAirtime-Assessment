package com.ebuka.xpress.service;

import com.ebuka.xpress.model.dto.base.BaseResponse;
import com.ebuka.xpress.model.dto.user.LoginRequest;
import com.ebuka.xpress.model.dto.user.SignUpRequest;
import com.ebuka.xpress.model.entity.User;

public interface UserService {

    BaseResponse<?> handleUserRegistration(SignUpRequest signUpRequest);

    BaseResponse<?> handleUserLogin(LoginRequest loginRequest);
    User getUserFromSecurityContext();
}
