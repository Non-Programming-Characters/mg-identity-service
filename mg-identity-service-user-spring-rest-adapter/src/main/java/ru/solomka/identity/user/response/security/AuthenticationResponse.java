package ru.solomka.identity.user.response.security;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Schema(description = "JWT tokens issued after successful authentication")
public class AuthenticationResponse {

    @Schema(
            description = "Access token (JWT) for API authorization",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.xxxxx"
    )
    @NonNull String accessToken;
}