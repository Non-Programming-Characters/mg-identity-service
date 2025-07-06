package ru.solomka.identity.user.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationRequest {

    @NonNull String login;

    @NonNull String password;

    @NonNull String firstName;

    @NonNull String lastName;

    @NonNull String email;

    @NonNull String birthDate;
}