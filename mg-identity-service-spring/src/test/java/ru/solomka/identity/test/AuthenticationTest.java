package ru.solomka.identity.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.solomka.identity.authentication.AuthenticationService;
import ru.solomka.identity.authentication.EncoderDelegateAdapter;
import ru.solomka.identity.authentication.exception.CredentialsException;
import ru.solomka.identity.common.EntityNotification;
import ru.solomka.identity.common.EntityNotificationService;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.principal.PrincipalRepository;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserRepository;
import ru.solomka.identity.user.UserService;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthenticationTest {

    @Mock
    PrincipalRepository principalRepository;

    @Mock
    UserRepository userRepository;

    private AuthenticationService  authenticationService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        PrincipalService principalService = new PrincipalService(principalRepository);
        UserService userService = new UserService(userRepository, new EntityNotificationService<>(new EntityNotification<>() {
            @Override
            public void notifyCreate(UserEntity message, PrincipalEntity entity) {
            }

            @Override
            public void notifyUpdate(UserEntity message, PrincipalEntity entity) {
            }

            @Override
            public void notifyDelete(UserEntity message, PrincipalEntity entity) {
            }
        }, principalService));

        authenticationService = new AuthenticationService(
                principalService, userService,
                new EncoderDelegateAdapter(passwordEncoder)
        );
    }

    @Test
    void shouldThrowWhenNotFoundUser() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> authenticationService.authenticate("unknownuser", "unknownpassword"));
    }

    @Test
    void shouldThrowWhenInvalidCredentials() {
        UserEntity user = UserEntity.builder()
                .login("testuserlogin")
                .passwordHash(passwordEncoder.encode("TestPassword"))
                .email("testemail")
                .build();

        Mockito.when(userRepository.findByLogin("testuserlogin")).thenReturn(user);
        Assertions.assertThrows(CredentialsException.class,
                () -> authenticationService.authenticate(user.getLogin(), "invalidpassword"));
    }

    @Test
    void shouldReturnPrincipalWhenCorrectParams() {
        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .login("testuserlogin")
                .passwordHash(passwordEncoder.encode("TestPassword"))
                .email("testemail")
                .build();

        PrincipalEntity principalEntity = PrincipalEntity.builder()
                .id(user.getId())
                .username(user.getLogin())
                .build();

        Mockito.when(userRepository.findByLogin("testuserlogin")).thenReturn(user);
        Mockito.when(authenticationService.authenticate("testuserlogin", "TestPassword")).thenReturn(principalEntity);
    }
}