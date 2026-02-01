package ru.solomka.identity.authentication.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Schema(description = "Basic information of the newly registered user")
public class SignupResponse {

    @Schema(description = "Assigned user login", example = "newuser123")
    @NonNull String login;

    @Schema(description = "Registered email address", example = "user@example.com")
    @NonNull String email;
}