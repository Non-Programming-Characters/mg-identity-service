package ru.solomka.identity.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Schema(description = "Public user profile information")
public class UserSearchResponse {

    @Schema(description = "Unique user identifier", example = "550e8400-e29b-41d4-a716-446655440000")
    @NonNull UUID id;

    @Schema(description = "Unique username", example = "johndoe")
    @NonNull String login;

    @Schema(description = "User's email address", example = "john.doe@example.com")
    @NonNull String email;

    @Schema(description = "Timestamp when the user account was created", example = "2025-01-12T10:30:00Z")
    @NonNull Instant createdAt;
}
