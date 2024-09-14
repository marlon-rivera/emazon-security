package com.emazon.security.domain.api;

import com.emazon.security.domain.model.Auth;
import com.emazon.security.domain.model.User;

public interface IUserServicePort {

    Auth saveClient(User user);
    Auth saveWarehouseAssistant(User user);
    Auth loginUser(String email, String password);

}
