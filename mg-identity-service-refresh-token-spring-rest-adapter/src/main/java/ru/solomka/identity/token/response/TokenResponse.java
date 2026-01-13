package ru.solomka.identity.token.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenResponse {

    @Schema(
            description = "Newly issued access token (JWT)",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.abc123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NonNull
    String accessToken;
}