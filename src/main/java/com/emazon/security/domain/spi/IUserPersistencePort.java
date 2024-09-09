package com.emazon.security.domain.spi;

import com.emazon.security.domain.model.Auth;
import com.emazon.security.domain.model.User;

import java.util.Optional;

public interface IUserPersistencePort {

    Auth saveUser(User user);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(String id);
    Auth loginUser(String email, String password);

}
