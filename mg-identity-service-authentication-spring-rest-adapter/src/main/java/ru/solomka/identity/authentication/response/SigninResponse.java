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
@Schema(description = "JWT tokens issued after successful authentication")
public class SigninResponse {


    @Schema(
            description = "Access token (JWT) for API authorization",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.xxxxx"
    )
    @NonNull String accessToken;
}
