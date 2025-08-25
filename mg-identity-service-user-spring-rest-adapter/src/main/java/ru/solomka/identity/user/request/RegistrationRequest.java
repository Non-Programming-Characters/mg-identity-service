package ru.solomka.identity.user.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationRequest {

    @NonNull String login;
    @NonNull String password;
    @NonNull String email;
    @NonNull String firstName;
    @NonNull String lastName;
    @NonNull Instant birthDate;
}