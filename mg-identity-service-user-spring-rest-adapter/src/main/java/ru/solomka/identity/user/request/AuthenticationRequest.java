package ru.solomka.identity.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Schema(description = "User credentials for authentication")
public class AuthenticationRequest {

    @Schema(description = "User login (username)", example = "johndoe", requiredMode = Schema.RequiredMode.REQUIRED)
    @NonNull String login;

    @Schema(description = "User password", example = "s3cr3tP@ss!", requiredMode = Schema.RequiredMode.REQUIRED)
    @NonNull String password;
}