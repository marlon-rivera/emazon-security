package com.emazon.security.adapters.driven.jpa.mysql.adapter;

import com.emazon.security.adapters.driven.jpa.mysql.entity.UserEntity;
import com.emazon.security.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.emazon.security.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.emazon.security.domain.model.Auth;
import com.emazon.security.domain.model.User;
import com.emazon.security.domain.spi.IAuthenticationPort;
import com.emazon.security.domain.spi.IJwtPort;
import com.emazon.security.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IJwtPort jwtPort;
    private final IAuthenticationPort authenticationPort;

    @Override
    public Auth saveUser(User user) {
        UserEntity userEntity = userRepository.save(userEntityMapper.toUserEntity(user));
        return new Auth(jwtPort.getToken(userEntity.getEmail(), userEntity.getRole().name()));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userEntityMapper.toUserOptional(userRepository.findByEmail(email));
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userEntityMapper.toUserOptional(userRepository.findById(id));
    }

    @Override
    public Auth loginUser(String email, String password) {
        authenticationPort.authenticate(email, password);
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow();
        return new Auth(jwtPort.getToken(userEntity.getEmail(), userEntity.getRole().name()));

    }
}
