package ru.solomka.identity.token.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenRefreshRequest {

    @Schema(
            description = "JWT refresh token issued during previous authentication",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.xxxxx",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NonNull String refreshToken;
}