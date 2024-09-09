package com.emazon.security.adapters.driving.http.controller;

import com.emazon.security.adapters.driving.http.dto.request.LoginRequest;
import com.emazon.security.adapters.driving.http.dto.request.UserRequest;
import com.emazon.security.adapters.driving.http.dto.response.AuthResponse;
import com.emazon.security.configuration.jwt.JWTAuthFilter;
import com.emazon.security.configuration.jwt.JwtService;
import com.emazon.security.domain.api.IUserServicePort;
import com.emazon.security.adapters.driving.http.mapper.request.IUserRequestMapper;
import com.emazon.security.adapters.driving.http.mapper.response.IUserResponseMapper;
import com.emazon.security.domain.model.Auth;
import com.emazon.security.domain.model.RoleEnum;
import com.emazon.security.domain.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IUserServicePort userService;

    @MockBean
    private IUserRequestMapper userRequestMapper;

    @MockBean
    private IUserResponseMapper userResponseMapper;

    @MockBean
    private JWTAuthFilter jwtAuthFilter;

    @InjectMocks
    private JwtService jwtService;

    public UserControllerTest() {
        openMocks(this);
    }

    @Test
    void testRegister() throws Exception {
        UserRequest userRequest = new UserRequest(
                BigInteger.valueOf(1093L), "test", "test", "+573507310045",
                LocalDate.of(2000, 1, 1), "user@user.com", "user123", RoleEnum.USER
        );
        AuthResponse authResponse = new AuthResponse("token");

        when(userRequestMapper.toUser(any(UserRequest.class))).thenReturn(new User());
        when(userService.saveUser(any(User.class))).thenReturn(new Auth("token"));
        when(userResponseMapper.toAuthResponse(any(Auth.class))).thenReturn(authResponse);

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("token"));
    }

    @Test
    void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user@user.com", "user123");
        AuthResponse authResponse = new AuthResponse("token");

        when(userService.loginUser(any(String.class), any(String.class))).thenReturn(new Auth("token"));
        when(userResponseMapper.toAuthResponse(any(Auth.class))).thenReturn(authResponse);

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"));
    }
}
