package ru.solomka.identity.user.response.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Schema(description = "Basic information of the newly registered user")
public class RegistrationResponse {

    @Schema(description = "Assigned user login", example = "newuser123")
    @NonNull String login;

    @Schema(description = "Registered email address", example = "user@example.com")
    @NonNull String email;
}