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
@Schema(description = "Credential value to be validated")
public class ValidateCredentialsRequest {

    @Schema(
            description = "The raw credential value (e.g., 'john_doe' or 'user@example.com')",
            example = "user@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NonNull
    String data;
}
