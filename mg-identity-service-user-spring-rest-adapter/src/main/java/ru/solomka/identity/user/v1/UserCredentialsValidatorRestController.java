package ru.solomka.identity.user.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.user.cqrs.command.ValidateUserCredentialCommand;
import ru.solomka.identity.user.request.ValidateCredentialsRequest;
import ru.solomka.identity.user.response.validation.UserValidateCredentialsResponse;

@RestController
@RequestMapping("/api/v1/identity/credentials/validator")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Credential Validation", description = "API for validating and returning normalized user credentials (e.g., login, email)")
public class UserCredentialsValidatorRestController {

    @NonNull
    CommandHandler<ValidateUserCredentialCommand, String> validateUserCredentialCommandHandler;

    @PostMapping(produces = "application/json")
    @Operation(
            summary = "Validate and return user credential",
            description = """
            Validates the format of a user-provided credential and returns it if valid.
            
            Supported types:
            - `login`: must start with a letter, contain only letters, digits, hyphens or dots, and be 2–21 characters long.
            - `email`: must conform to standard email format.
            
            If validation fails, an error is thrown. If successful, the original value is returned in the `payload` field.
            """
    )
    @Parameter(
            name = "type",
            description = "Type of credential to validate: `login` or `email`",
            example = "email",
            required = true
    )
    @ApiResponse(
            responseCode = "200",
            description = "Credential is valid",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserValidateCredentialsResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Bad Request: Invalid `type` parameter, missing, or empty payload",
            content = @Content
    )
    @ApiResponse(
            responseCode = "403",
            description = "Forbidden: Credential format is invalid (e.g., malformed email or disallowed login pattern)",
            content = @Content
    )
    public ResponseEntity<UserValidateCredentialsResponse> validateCredential(
            @RequestParam("type") String type,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credential value to validate",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ValidateCredentialsRequest.class))
            )
            @RequestBody ValidateCredentialsRequest credentialsRequest
    ) {
        String validatedData = validateUserCredentialCommandHandler.handle(
                new ValidateUserCredentialCommand(type, credentialsRequest.getData())
        );
        return ResponseEntity.ok(new UserValidateCredentialsResponse(validatedData));
    }
}