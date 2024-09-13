package com.emazon.security.adapters.driven.jpa.mysql.adapter;

import com.emazon.security.adapters.driven.jpa.mysql.entity.UserEntity;
import com.emazon.security.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.emazon.security.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.emazon.security.domain.model.Auth;
import com.emazon.security.domain.model.RoleEnum;
import com.emazon.security.domain.model.User;
import com.emazon.security.domain.spi.IAuthenticationPort;
import com.emazon.security.domain.spi.IJwtPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @Mock
    private IJwtPort jwtPort;

    @Mock
    private IAuthenticationPort authenticationPort;

    @InjectMocks
    private UserAdapter userAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(RoleEnum.ADMIN);

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");
        userEntity.setId("123");
        userEntity.setRole(RoleEnum.ADMIN);

        when(userEntityMapper.toUserEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(jwtPort.getToken(userEntity.getId(), userEntity.getRole().name())).thenReturn("jwtToken");

        Auth auth = userAdapter.saveUser(user);

        assertEquals("jwtToken", auth.getToken());
    }

    @Test
    void testGetUserByEmail() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");

        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUserOptional(Optional.of(userEntity))).thenReturn(Optional.of(user));

        Optional<User> result = userAdapter.getUserByEmail("test@example.com");

        assertEquals(Optional.of(user), result);
    }

    @Test
    void testGetUserById() {
        String userId = "123";
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUserOptional(Optional.of(userEntity))).thenReturn(Optional.of(user));

        Optional<User> result = userAdapter.getUserById(userId);

        assertEquals(Optional.of(user), result);
    }

    @Test
    void loginUser_ShouldReturnAuth_WhenLoginIsSuccessful() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId("123");
        userEntity.setEmail("test@example.com");
        userEntity.setRole(RoleEnum.ADMIN);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(userEntity));
        when(jwtPort.getToken(userEntity.getId(), userEntity.getRole().name())).thenReturn("jwtToken");

        Auth auth = userAdapter.loginUser("test@example.com", "password");

        assertNotNull(auth);
        assertEquals("jwtToken", auth.getToken());
            verify (authenticationPort, times(1)).authenticate("123", "password");
    }

    @Test
    void loginUser_ShouldThrowException_WhenAuthenticationFails() {
        when(userRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userAdapter.loginUser("test@example.com", "password"));
        verify(authenticationPort, times(0)).authenticate("123", "password");
    }
}
