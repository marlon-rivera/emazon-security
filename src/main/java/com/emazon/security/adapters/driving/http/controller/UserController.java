package com.emazon.security.adapters.driving.http.controller;

import com.emazon.security.adapters.driving.http.dto.request.UserRequest;
import com.emazon.security.adapters.driving.http.dto.response.AuthResponse;
import com.emazon.security.adapters.driving.http.mapper.request.IUserRequestMapper;
import com.emazon.security.adapters.driving.http.mapper.response.IUserResponseMapper;
import com.emazon.security.configuration.exceptionhandler.ExceptionResponse;
import com.emazon.security.domain.api.IUserServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserServicePort userService;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;

    @Operation(summary = "Create a new user", description = "This endpoint allows you to create a new user.")
    @ApiResponse(responseCode = "200", description = "User created correctly.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class)))
    @ApiResponse(responseCode = "400", description = "Incorrect user creation request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))})
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid
                                             @RequestBody UserRequest userRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseMapper.toAuthResponse(userService.saveUser(userRequestMapper.toUser(userRequest))));
    }

}
