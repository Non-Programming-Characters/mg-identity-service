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
import ru.solomka.identity.authentication.EncoderDelegate;
import ru.solomka.identity.authentication.EncoderDelegateAdapter;
import ru.solomka.identity.authentication.exception.CredentialsException;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.principal.PrincipalRepository;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserRepository;
import ru.solomka.identity.user.UserService;

import java.time.Instant;
import java.util.Optional;
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
        authenticationService = new AuthenticationService(
                new PrincipalService(principalRepository),
                new UserService(userRepository),
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
                .id(UUID.randomUUID())
                .login("testuserlogin")
                .passwordHash(passwordEncoder.encode("TestPassword"))
                .firstName("testfirstname")
                .lastName("testlastname")
                .email("testemail")
                .createdAt(Instant.now())
                .birthDate(Instant.now())
                .build();

        Mockito.when(userRepository.findByLogin("testuserlogin")).thenReturn(Optional.of(user));
        Assertions.assertThrows(CredentialsException.class,
                () -> authenticationService.authenticate(user.getLogin(), "invalidpassword"));
    }

    @Test
    void shouldReturnPrincipalWhenCorrectParams() {
        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .login("testuserlogin")
                .passwordHash(passwordEncoder.encode("TestPassword"))
                .firstName("testfirstname")
                .lastName("testlastname")
                .email("testemail")
                .createdAt(Instant.now())
                .birthDate(Instant.now())
                .build();

        PrincipalEntity principalEntity = PrincipalEntity.builder()
                .id(user.getId())
                .username(user.getLogin())
                .build();

        Mockito.when(userRepository.findByLogin("testuserlogin")).thenReturn(Optional.of(user));
        Mockito.when(authenticationService.authenticate("testuserlogin", "TestPassword")).thenReturn(principalEntity);
    }
}