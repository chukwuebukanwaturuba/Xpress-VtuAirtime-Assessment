package com.ebuka.xpress.builder;

import com.ebuka.xpress.model.dto.user.SignUpRequest;
import com.ebuka.xpress.model.entity.Contact;
import com.ebuka.xpress.model.entity.User;
import com.ebuka.xpress.model.enums.UserType;

public class UserRecordBuilder {
    public static User mapSignupRequestToUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setUserType(UserType.CUSTOMER);
        user.setEmail(signUpRequest.getEmailAddress());
        user.setBlocked(false);
        user.setActive(true);
        return user;
    }
}
