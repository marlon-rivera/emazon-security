package com.emazon.security.adapters.driven.jwt;

import com.emazon.security.configuration.jwt.JwtService;
import com.emazon.security.domain.spi.IJwtPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAdapter implements IJwtPort {

    private final JwtService jwtService;

    @Override
    public String getToken(String email, String role) {
        return jwtService.getToken(email, role);
    }
}
