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
import ru.solomka.identity.common.EntityNotification;
import ru.solomka.identity.common.EntityNotificationService;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserRepository;
import ru.solomka.identity.user.UserService;
import ru.solomka.identity.user.UserStatus;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock private UserRepository userRepository;

    @Mock private EntityNotification<UserEntity> userNotification;

    private UserService userService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, new EntityNotificationService<>(userNotification));
    }

    @Nested
    @DisplayName("Создание пользователя")
    class CreateUser {

        @Test
        @DisplayName("Создаёт пользователя и отправляет уведомление о создании")
        void shouldCreateUserAndNotify() {
            UserEntity newUser = UserEntity.builder()
                    .login("newuser")
                    .passwordHash(passwordEncoder.encode("password"))
                    .email("newuser@example.com")
                    .status(UserStatus.NOT_VERIFIED)
                    .build();

            UserEntity savedUser = UserEntity.builder()
                    .id(UUID.randomUUID())
                    .login("newuser")
                    .passwordHash(passwordEncoder.encode("password"))
                    .email("newuser@example.com")
                    .status(UserStatus.NOT_VERIFIED)
                    .createdAt(Instant.now())
                    .build();

            given(userRepository.create(newUser)).willReturn(savedUser);

            UserEntity result = userService.create(newUser);

            assertThat(result).usingRecursiveComparison().ignoringFields("createdAt").isEqualTo(savedUser);
            verify(userRepository).create(newUser);
            verify(userNotification).notifyCreate(savedUser);
        }
    }

    @Nested
    @DisplayName("Обновление пользователя")
    class UpdateUser {

        @Test
        @DisplayName("Обновляет существующего пользователя и отправляет уведомление об обновлении")
        void shouldUpdateUserAndNotify() {
            UserEntity existingUser = UserEntity.builder()
                    .id(UUID.randomUUID())
                    .login("existinguser")
                    .passwordHash(passwordEncoder.encode("oldpass"))
                    .email("old@example.com")
                    .status(UserStatus.VERIFIED)
                    .createdAt(Instant.now())
                    .build();

            UserEntity updatedUser = UserEntity.builder()
                    .id(existingUser.getId())
                    .login("existinguser")
                    .passwordHash(passwordEncoder.encode("newpass"))
                    .email("new@example.com")
                    .status(UserStatus.NOT_VERIFIED)
                    .createdAt(existingUser.getCreatedAt())
                    .build();

            given(userRepository.update(updatedUser)).willReturn(updatedUser);

            UserEntity result = userService.update(updatedUser);

            assertThat(result).isEqualTo(updatedUser);
            verify(userRepository).update(updatedUser);
            verify(userNotification).notifyUpdate(updatedUser);
        }
    }

    @Nested
    @DisplayName("Удаление пользователя")
    class DeleteUser {

        @Test
        @DisplayName("Удаляет существующего пользователя и отправляет уведомление об удалении")
        void shouldDeleteUserAndNotify() {
            UserEntity userToDelete = UserEntity.builder()
                    .id(UUID.randomUUID())
                    .login("todelete")
                    .passwordHash(passwordEncoder.encode("pass"))
                    .email("delete@example.com")
                    .status(UserStatus.NOT_VERIFIED)
                    .createdAt(Instant.now())
                    .build();

            UUID userId = userToDelete.getId();

            given(userRepository.existsById(userId)).willReturn(true);
            given(userRepository.deleteById(userId)).willReturn(userToDelete);

            UserEntity deleted = userService.deleteById(userId);

            assertThat(deleted).isEqualTo(userToDelete);
            verify(userRepository).deleteById(userId);
            verify(userNotification).notifyDelete(userToDelete);
        }

        @Test
        @DisplayName("Выбрасывает EntityNotFoundException при удалении несуществующего пользователя")
        void shouldThrowEntityNotFoundExceptionWhenDeletingNonExistentUser() {
            UUID nonExistentId = UUID.randomUUID();
            given(userRepository.existsById(nonExistentId)).willReturn(false);

            assertThatThrownBy(() -> userService.deleteById(nonExistentId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining(nonExistentId.toString());
        }
    }

    @Nested
    @DisplayName("Получение пользователя по логину")
    class GetUserByLogin {

        @Test
        @DisplayName("Выбрасывает EntityNotFoundException, если пользователь не найден")
        void shouldThrowEntityNotFoundExceptionWhenUserNotFound() {
            String login = "invalidlogin";
            given(userRepository.findUserByLogin(login)).willReturn(Optional.empty());

            assertThatThrownBy(() -> userService.getUserByLogin(login))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("User with login '" + login + "' not found");
        }

        @Test
        @DisplayName("Возвращает пользователя, если он существует")
        void shouldReturnUserWhenFoundByLogin() {
            UserEntity expectedUser = UserEntity.builder()
                    .id(UUID.randomUUID())
                    .login("testuserlogin")
                    .passwordHash(passwordEncoder.encode("TestPassword"))
                    .email("testemail")
                    .status(UserStatus.NOT_VERIFIED)
                    .build();

            given(userRepository.findUserByLogin("testuserlogin")).willReturn(Optional.of(expectedUser));

            UserEntity result = userService.getUserByLogin("testuserlogin");

            assertThat(result.getLogin()).isEqualTo(expectedUser.getLogin());
            assertThat(result.getEmail()).isEqualTo(expectedUser.getEmail());
            assertThat(result.getId()).isEqualTo(expectedUser.getId());
        }
    }

    @Nested
    @DisplayName("Поиск пользователя (возвращающий Optional)")
    class FindUser {

        @Test
        @DisplayName("Возвращает Optional с пользователем при поиске по логину")
        void shouldReturnUserOptionalWhenFoundByLogin() {
            UserEntity user = UserEntity.builder()
                    .id(UUID.randomUUID())
                    .login("testuserlogin")
                    .passwordHash(passwordEncoder.encode("TestPassword"))
                    .email("testemail")
                    .status(UserStatus.NOT_VERIFIED)
                    .build();

            given(userRepository.findUserByLogin("testuserlogin")).willReturn(Optional.of(user));

            Optional<UserEntity> result = userService.findUserByLogin("testuserlogin");

            assertThat(result).isPresent();
            assertThat(result.get().getLogin()).isEqualTo("testuserlogin");
            assertThat(result.get().getEmail()).isEqualTo("testemail");
        }

        @Test
        @DisplayName("Возвращает Optional.empty при поиске несуществующего пользователя")
        void shouldReturnEmptyOptionalWhenUserNotFoundByLogin() {
            String login = "nonexistent";
            given(userRepository.findUserByLogin(login)).willReturn(Optional.empty());

            Optional<UserEntity> result = userService.findUserByLogin(login);

            assertThat(result).isEmpty();
        }
    }
}