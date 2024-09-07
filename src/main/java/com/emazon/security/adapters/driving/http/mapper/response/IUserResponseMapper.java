package com.emazon.security.adapters.driving.http.mapper.response;

import com.emazon.security.adapters.driving.http.dto.response.AuthResponse;
import com.emazon.security.domain.model.Auth;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserResponseMapper {

    AuthResponse toAuthResponse(Auth auth);

}
