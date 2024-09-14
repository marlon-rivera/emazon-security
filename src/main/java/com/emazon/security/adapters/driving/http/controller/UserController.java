package com.emazon.security.adapters.driving.http.controller;

import com.emazon.security.adapters.driving.http.dto.request.LoginRequest;
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

    @Operation(summary = "Create a new client", description = "This endpoint allows you to create a new client.")
    @ApiResponse(responseCode = "200", description = "User created correctly.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class)))
    @ApiResponse(responseCode = "400", description = "Incorrect client creation request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))})
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid
                                             @RequestBody UserRequest userRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseMapper.toAuthResponse(userService.saveClient(userRequestMapper.toUser(userRequest))));
    }

    @Operation(summary = "Create a new warehouse assistant", description = "This endpoint allows you to create a new warehouse assistant.")
    @ApiResponse(responseCode = "200", description = "warehouse assistant created correctly.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class)))
    @ApiResponse(responseCode = "400", description = "Incorrect warehouse assistant creation request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))})
    @PostMapping("/registerWarehouse")
    public ResponseEntity<AuthResponse> registerWarehouse(@Valid
                                                 @RequestBody UserRequest userRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseMapper.toAuthResponse(userService.saveWarehouseAssistant(userRequestMapper.toUser(userRequest))));
    }

    @Operation(
            summary = "Login a user",
            description = "This endpoint allows a user to log in."
    )
    @ApiResponse(
            responseCode = "200",
            description = "User logged in successfully.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid login credentials",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)
            )
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.status(HttpStatus.OK).body(userResponseMapper.toAuthResponse(userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword())));
    }

}
