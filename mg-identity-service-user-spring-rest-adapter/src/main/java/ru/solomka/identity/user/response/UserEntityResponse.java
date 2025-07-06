package ru.solomka.identity.user.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class UserEntityResponse {

    UUID id;

    @NonNull
    String login;

    @NonNull String email;

    @NonNull String firstName;
    @NonNull String lastName;
    @NonNull String bio;
    @NonNull
    Instant birthDate;

    Instant createdAt;
}