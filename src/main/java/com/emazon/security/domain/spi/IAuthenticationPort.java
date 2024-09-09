package com.emazon.security.domain.spi;

public interface IAuthenticationPort {

    void authenticate(String username, String password);

}
