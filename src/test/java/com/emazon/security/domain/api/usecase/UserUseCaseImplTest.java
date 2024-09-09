package com.emazon.security.domain.api.usecase;

import com.emazon.security.domain.model.Auth;
import com.emazon.security.domain.model.User;
import com.emazon.security.domain.spi.IUserPersistencePort;
import com.emazon.security.domain.spi.IEncoderPort;
import com.emazon.security.domain.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserUseCaseImplTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IEncoderPort encoderPort;

    @InjectMocks
    private UserUseCaseImpl userUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser_ValidUser() {
        User user = new User();
        user.setId("123");
        user.setEmail("test@example.com");
        user.setPhone("+573214567894");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setPassword("password");

        when(userPersistencePort.getUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserById(user.getId())).thenReturn(Optional.empty());
        when(encoderPort.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userPersistencePort.saveUser(user)).thenReturn(new Auth("jwtToken"));

        Auth auth = userUseCase.saveUser(user);

        assertNotNull(auth);
        assertEquals("jwtToken", auth.getToken());
    }

    @Test
    void testSaveUser_EmailAlreadyUsed() {
        User user = new User();
        user.setId("123");
        user.setEmail("test@example.com");
        user.setPhone("+573214567894");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setPassword("password");

        when(userPersistencePort.getUserByEmail(user.getEmail())).thenReturn(Optional.of(new User()));

        UserEmailAlreadyUsedException thrown = assertThrows(UserEmailAlreadyUsedException.class, () -> userUseCase.saveUser(user));
        assertNotNull(thrown);
    }

    @Test
    void testSaveUser_IdAlreadyUsed() {
        User user = new User();
        user.setId("123");
        user.setEmail("test@example.com");
        user.setPhone("+573214567894");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setPassword("password");

        when(userPersistencePort.getUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserById(user.getId())).thenReturn(Optional.of(new User()));

        UserIdAlreadyUsedException thrown = assertThrows(UserIdAlreadyUsedException.class, () -> userUseCase.saveUser(user));
        assertNotNull(thrown);
    }

    @Test
    void testSaveUser_NotAdult() {
        User user = new User();
        user.setId("123");
        user.setEmail("test@example.com");
        user.setPhone("+573214567894");
        user.setBirthDate(LocalDate.of(2020, 1, 1));
        user.setPassword("password");

        when(userPersistencePort.getUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserById(user.getId())).thenReturn(Optional.empty());

        UserNotLegalAgeException thrown = assertThrows(UserNotLegalAgeException.class, () -> userUseCase.saveUser(user));
        assertNotNull(thrown);
    }

    @Test
    void testValidatePhone_InvalidPhone() {
        User user = new User();
        user.setId("123");
        user.setEmail("test@example.com");
        user.setPhone("54567894");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setPassword("password");
        assertThrows(UserPhoneNotValidException.class, () -> userUseCase.saveUser(user));
        user.setPhone("a12343a");
        assertThrows(UserPhoneNotValidException.class, () -> userUseCase.saveUser(user));
    }

    @Test
    void testValidateEmail_InvalidEmail() {
        User user = new User();
        user.setId("123");
        user.setEmail("test@example");
        user.setPhone("+573214567894");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setPassword("password");
        assertThrows(UserEmailNotValidException.class, () -> userUseCase.saveUser(user));
    }

    @Test
    void testValidateId_InvalidId() {
        User user = new User();
        user.setId("123a");
        user.setEmail("test@example.com");
        user.setPhone("+573214567894");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setPassword("password");
        assertThrows(UserIdNotValidOnlyNumericException.class, () -> userUseCase.saveUser(user));
    }

    @Test
    void loginUser_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        when(userPersistencePort.getUserByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userUseCase.loginUser("test@example.com", "password123"));
    }

    @Test
    void loginUser_ShouldThrowUserIncorrectPasswordException_WhenPasswordIsIncorrect() {
        User user = new User();
        user.setPassword("encodedPassword");

        when(userPersistencePort.getUserByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(encoderPort.matches("password123", user.getPassword())).thenReturn(false);

        assertThrows(UserIncorrectPasswordException.class, () -> userUseCase.loginUser("test@example.com", "password123"));
    }

    @Test
    void loginUser_ShouldReturnAuth_WhenLoginIsSuccessful() {
        User user = new User();
        user.setPassword("encodedPassword");
        Auth auth = new Auth("token");

        when(userPersistencePort.getUserByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(encoderPort.matches("password123", user.getPassword())).thenReturn(true);
        when(userPersistencePort.loginUser("test@example.com", "password123")).thenReturn(auth);

        Auth result = userUseCase.loginUser("test@example.com", "password123");

        assertEquals(auth, result);
    }
}
