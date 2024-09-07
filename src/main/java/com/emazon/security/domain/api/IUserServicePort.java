package com.emazon.security.domain.api;

import com.emazon.security.domain.model.Auth;
import com.emazon.security.domain.model.User;

public interface IUserServicePort {

    Auth saveUser(User user);

}
