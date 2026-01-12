package ru.solomka.identity.user.response.validation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Schema(description = "Response containing the validated credential value")
public class UserValidateCredentialsResponse {

    @Schema(
            description = "The input credential value, returned unchanged if it passed format validation",
            example = "john_doe123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NonNull
    String payload;
}