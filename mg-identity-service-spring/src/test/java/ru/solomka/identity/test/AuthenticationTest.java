package ru.solomka.identity.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import ru.solomka.identity.user.UserStatus;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthenticationTest {

    @Mock
    private PrincipalRepository principalRepository;

    @Mock
    private UserRepository userRepository;

    private AuthenticationService authenticationService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        PrincipalService principalService = new PrincipalService(principalRepository);
        UserService userService = new UserService(userRepository, new EntityNotificationService<>(new EntityNotification<>() {
            @Override
            public void notifyCreate(UserEntity message) {
            }

            @Override
            public void notifyUpdate(UserEntity message) {
            }

            @Override
            public void notifyDelete(UserEntity message) {
            }
        }));

        authenticationService = new AuthenticationService(
                principalService,
                userService,
                new EncoderDelegateAdapter(passwordEncoder)
        );
    }

    @Nested
    @DisplayName("Аутентификация пользователя")
    class AuthenticateUser {

        @Test
        @DisplayName("Выбрасывает EntityNotFoundException, если пользователь не найден")
        void shouldThrowEntityNotFoundExceptionWhenUserNotFound() {
            String login = "unknownuser";
            String password = "unknownpassword";

            given(userRepository.findUserByLogin(login)).willReturn(Optional.empty());

            assertThatThrownBy(() -> authenticationService.authenticate(login, password))
                    .isInstanceOf(EntityNotFoundException.class);
        }

        @Test
        @DisplayName("Выбрасывает CredentialsException при неверном пароле")
        void shouldThrowCredentialsExceptionWhenPasswordIsInvalid() {
            UserEntity user = UserEntity.builder()
                    .id(UUID.randomUUID())
                    .login("testuserlogin")
                    .passwordHash(passwordEncoder.encode("TestPassword"))
                    .email("testemail")
                    .status(UserStatus.NOT_VERIFIED)
                    .build();

            given(userRepository.findUserByLogin("testuserlogin")).willReturn(Optional.of(user));

            assertThatThrownBy(() -> authenticationService.authenticate("testuserlogin", "invalidpassword"))
                    .isInstanceOf(CredentialsException.class);
        }

        @Test
        @DisplayName("Возвращает PrincipalEntity при корректных учетных данных")
        void shouldReturnPrincipalEntityWhenCredentialsAreValid() {
            UserEntity user = UserEntity.builder()
                    .id(UUID.randomUUID())
                    .login("testuserlogin")
                    .passwordHash(passwordEncoder.encode("TestPassword"))
                    .email("testemail")
                    .status(UserStatus.NOT_VERIFIED)
                    .build();

            given(userRepository.findUserByLogin("testuserlogin")).willReturn(Optional.of(user));
            given(principalRepository.setPrincipal(any(PrincipalEntity.class))).willAnswer(inv -> inv.getArgument(0));

            PrincipalEntity result = authenticationService.authenticate("testuserlogin", "TestPassword");

            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(user.getId());
            assertThat(result.getUsername()).isEqualTo(user.getLogin());
        }
    }
}