package com.emazon.security.adapters.driven.jpa.mysql.adapter;

import com.emazon.security.adapters.driven.jpa.mysql.entity.UserEntity;
import com.emazon.security.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.emazon.security.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.emazon.security.domain.model.Auth;
import com.emazon.security.domain.model.RoleEnum;
import com.emazon.security.domain.model.User;
import com.emazon.security.domain.spi.IJwtPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @Mock
    private IJwtPort jwtPort;

    @InjectMocks
    private UserAdapter userAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        User user = new User(); // Configura el usuario como necesites
        user.setEmail("test@example.com");
        user.setRole(RoleEnum.ADMIN);

        UserEntity userEntity = new UserEntity(); // Configura el UserEntity como necesites
        userEntity.setEmail("test@example.com");
        userEntity.setRole(RoleEnum.ADMIN);

        when(userEntityMapper.toUserEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(jwtPort.getToken(userEntity.getEmail(), userEntity.getRole().name())).thenReturn("jwtToken");

        Auth auth = userAdapter.saveUser(user);

        assertEquals("jwtToken", auth.getToken());
    }

    @Test
    void testGetUserByEmail() {
        UserEntity userEntity = new UserEntity(); // Configura el UserEntity como necesites
        userEntity.setEmail("test@example.com");

        User user = new User(); // Configura el User como necesites
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUserOptional(Optional.of(userEntity))).thenReturn(Optional.of(user));

        Optional<User> result = userAdapter.getUserByEmail("test@example.com");

        assertEquals(Optional.of(user), result);
    }

    @Test
    void testGetUserById() {
        String userId = "123";
        UserEntity userEntity = new UserEntity(); // Configura el UserEntity como necesites
        userEntity.setId(userId);

        User user = new User(); // Configura el User como necesites
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUserOptional(Optional.of(userEntity))).thenReturn(Optional.of(user));

        Optional<User> result = userAdapter.getUserById(userId);

        assertEquals(Optional.of(user), result);
    }
}
