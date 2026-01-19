package ru.solomka.identity.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_users_login", columnList = "login", unique = true),
        @Index(name = "idx_users_email", columnList = "email", unique = true)
})
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class JpaUserEntity {

    @Id
    @Column(name = "id", nullable = false)
    @NonNull UUID id;

    @Column(name = "login", unique = true, nullable = false)
    @NonNull String login;

    @Column(name = "password_hash", nullable = false)
    @NonNull String passwordHash;

    @Column(name = "email", unique = true, nullable = false)
    @NonNull String email;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull UserStatus status;

    @Column(name = "created_at", nullable = false)
    @NonNull Instant createdAt;
}