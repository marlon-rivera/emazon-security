package com.emazon.security.adapters.driving.http.mapper.request;

import com.emazon.security.adapters.driving.http.dto.request.UserRequest;
import com.emazon.security.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserRequestMapper {

    User toUser(UserRequest userRequest);

}
