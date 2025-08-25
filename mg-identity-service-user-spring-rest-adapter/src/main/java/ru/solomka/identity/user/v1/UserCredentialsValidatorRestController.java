package ru.solomka.identity.user.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.user.cqrs.command.ValidateUserCredentialCommand;
import ru.solomka.identity.user.request.ValidateCredentialsRequest;
import ru.solomka.identity.user.response.validation.UserValidateCredentialsResponse;

@RestController
@RequestMapping("/v1/credentials/validator")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCredentialsValidatorRestController {

    @NonNull CommandHandler<ValidateUserCredentialCommand, String> validateUserCredentialCommandHandler;

    @Operation(
            summary = "Validate user credentials",
            method = "POST",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidateCredentialsRequest.class)),
                    useParameterTypeSchema = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "The user's credentials are valid",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserValidateCredentialsResponse.class),
                            examples = {
                                    @ExampleObject(name = "200", value = "{\"payload\": valeracontest1421" + "}",
                                            description = "Returns the input data after validation")
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403", description = "Exception: The user's credentials are not valid",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping(produces = "application/json")
    public ResponseEntity<UserValidateCredentialsResponse> getUserById(@RequestParam("type") String userFieldValidate,
                                                                       @RequestBody ValidateCredentialsRequest credentialsRequest) {
        ValidateUserCredentialCommand validateUserCredentialCommand = new ValidateUserCredentialCommand(userFieldValidate, credentialsRequest.getData());
        return ResponseEntity.ok(
                new UserValidateCredentialsResponse(
                        validateUserCredentialCommandHandler.handle(validateUserCredentialCommand)
                )
        );
    }
}