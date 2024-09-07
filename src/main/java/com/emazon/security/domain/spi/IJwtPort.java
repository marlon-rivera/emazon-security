package com.emazon.security.domain.spi;

public interface IJwtPort {

    String getToken(String email, String role);

}
