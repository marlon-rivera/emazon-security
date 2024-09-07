package com.emazon.security.adapters.driven.jpa.mysql.mapper;


import com.emazon.security.adapters.driven.jpa.mysql.entity.UserEntity;
import com.emazon.security.domain.model.User;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface IUserEntityMapper {

    UserEntity toUserEntity(User user);

    User toUser(UserEntity entity);

    default Optional<User> toUserOptional(Optional<UserEntity> userEntityOptional) {
        return userEntityOptional.map(this::toUser);
    }

}
