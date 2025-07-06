package ru.solomka.identity.user;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.Entity;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity implements Entity {

    UUID id;

    @NonNull String login;

    @NonNull String passwordHash;

    @NonNull String phoneNumber;
    @NonNull String email;

    @NonNull String firstName;
    @NonNull String lastName;
    @NonNull String bio;
    @NonNull Instant birthDate;

    Instant createdAt;
}