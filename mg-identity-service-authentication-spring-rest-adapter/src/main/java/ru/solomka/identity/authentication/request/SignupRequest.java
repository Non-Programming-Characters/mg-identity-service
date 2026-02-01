package ru.solomka.identity.authentication.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Schema(description = "New user registration data")
public class SignupRequest {

    @Schema(description = "Desired unique login", example = "newuser123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NonNull String login;

    @Schema(description = "Account password", example = "MyStr0ngP@ss!", requiredMode = Schema.RequiredMode.REQUIRED)
    @NonNull String password;

    @Schema(description = "Valid email address", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NonNull String email;
}