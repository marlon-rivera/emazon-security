package com.emazon.security.domain.spi;

public interface IJwtPort {

    String getToken(String id, String role);

}
