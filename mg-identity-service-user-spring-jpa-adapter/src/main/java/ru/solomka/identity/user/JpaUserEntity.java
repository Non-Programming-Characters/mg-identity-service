package ru.solomka.identity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class JpaUserEntity {

    @Id
    @Column(name = "id", nullable = false)
    UUID id;

    @Column(name = "login", nullable = false)
    @NonNull String login;

    // TODO: RSA-256 ON THIS FIELD
    @Column(name = "password_hash", nullable = false)
    @NonNull String passwordHash;

    // TODO: RSA-256 ON THIS FIELD
    @Column(name = "phone_number", nullable = false)
    @NonNull String phoneNumber;

    @Column(name = "email", nullable = false)
    @NonNull String email;

    @Column(name = "first_name", nullable = false)
    @NonNull String firstName;

    @Column(name = "last_name", nullable = false)
    @NonNull String lastName;

    @Column(name = "bio", nullable = false)
    @NonNull String bio;

    @Column(name = "birth_date", nullable = false)
    @NonNull Instant birthDate;

    @Column(name = "created_at", nullable = false)
    Instant createdAt;
}
