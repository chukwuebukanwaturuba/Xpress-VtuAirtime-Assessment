package com.ebuka.xpress;

import com.ebuka.xpress.security.XpressJwtService;
import com.ebuka.xpress.model.dto.base.BaseResponse;
import com.ebuka.xpress.model.dto.user.AuthenticationToken;
import com.ebuka.xpress.model.dto.user.LoginRequest;
import com.ebuka.xpress.model.dto.user.SignUpRequest;
import com.ebuka.xpress.model.entity.User;
import com.ebuka.xpress.model.enums.UserType;
import com.ebuka.xpress.repository.UserRepository;
import com.ebuka.xpress.service.impl.UserServiceImpl;
import com.ebuka.xpress.utils.PasswordUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PasswordUtils passwordUtils;

    private AutoCloseable autoCloseable;
    private final AuthenticationProvider authenticationProvider = mock(AuthenticationProvider.class);
    private final XpressJwtService xpressJwtService = mock(XpressJwtService.class);
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    public void setAutoCloseable() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void shouldRegisterUser(){
        SignUpRequest request = new SignUpRequest();
        request.setEmailAddress("ebusclement@gmail.com");
        request.setFirstName("Nwaturuba");
        request.setLastName("Chukwuebuka");
        request.setPhoneNumber("07038559543");
        request.setPassword("Chuks@5037");

        User user = new User();
        user.setEmail(request.getEmailAddress());
        user.setUserType(UserType.CUSTOMER);
        user.setPassword("Chuza1@ok");

        when(passwordEncoder.encode(request.getPassword())).thenReturn("Chuza1@ok");
        when(passwordUtils.hashedPassword(request.getPassword())).thenReturn("Chuza1@ok");
        when(userRepository.save(any(User.class))).thenReturn(user);

        BaseResponse<?> baseResponse = userServiceImpl.handleUserRegistration(request);
        assertNotNull(baseResponse);
        assertEquals("user registered successfully", baseResponse.getResponseMessage());
    }

    @Test
    void testHandleUserLogin_Success() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "Chuza1@ok");

        User user = new User();
        user.setEmail("ebusclement@gmail.com");
        user.setUserType(UserType.CUSTOMER);
        user.setPassword("Chuza1@ok");
        AuthenticationToken mockToken = new AuthenticationToken("accessToke", "refreshToken");

        // Mock the behavior of userRepository
        when(userRepository.findUserByEmail(loginRequest.getEmailAddress())).thenReturn(Optional.of(user));

        // Mock the behavior of xpressJwtService
        when(xpressJwtService.generateToken(user)).thenReturn(mockToken);

        // Call the method under test
        BaseResponse<?> response = userServiceImpl.handleUserLogin(loginRequest);

        // Verify the interaction with dependencies and assertions on the response
        verify(authenticationProvider, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findUserByEmail(loginRequest.getEmailAddress());
        verify(xpressJwtService, times(1)).generateToken(user);

        // Assertions
        // Replace these assertions with your expected values based on the response
        assertEquals(200, response.getResponseCode());
        assertNotNull(response);
        // Assert other properties of the response as needed
    }


}

