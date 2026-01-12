package ru.solomka.identity.verification.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.verification.cqrs.VerificationHandleProcessCommand;
import ru.solomka.identity.verification.cqrs.VerificationPushProcessCommand;
import ru.solomka.identity.verification.request.VerificationHandleRequest;
import ru.solomka.identity.verification.request.VerificationPushRequest;
import ru.solomka.identity.verification.response.VerificationResponse;

@RestController
@RequestMapping("/api/v1/identity/verification")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Identity Verification", description = "API for managing user verification process (requesting and validating confirmation codes)")
public class VerificationNotifyRestController {

    @NonNull CommandHandler<VerificationHandleProcessCommand, Boolean> verificationHandleProcessCommandHandler;

    @NonNull CommandHandler<VerificationPushProcessCommand, Boolean> verificationPushProccessCommandHandler;

    @PostMapping(value = "/handle", produces = "application/json")
    @Operation(
            summary = "Validate confirmation code",
            description = "Submits a confirmation code for validation after the user receives it (e.g., via SMS or email)."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Confirmation code validated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VerificationResponse.class))
    )
    @ApiResponse(responseCode = "400", description = "Invalid request (missing userId or payload)", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    public ResponseEntity<VerificationResponse> handleVerificationProcess(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Verification handle request containing user ID and confirmation code",
                    required = true,
                    content = @Content(schema = @Schema(implementation = VerificationHandleRequest.class))
            )
            @RequestBody VerificationHandleRequest verificationHandleRequest
    ) {
        VerificationHandleProcessCommand verificationHandleProcessCommand = new VerificationHandleProcessCommand(
                verificationHandleRequest.getUserId(),
                verificationHandleRequest.getPayload()
        );
        return ResponseEntity.ok(new VerificationResponse(
                verificationHandleProcessCommandHandler.handle(verificationHandleProcessCommand)
        ));
    }

    @PostMapping(value = "/push", produces = "application/json")
    @Operation(
            summary = "Request confirmation code",
            description = "Triggers a new confirmation code to be sent to the user (e.g., resend SMS/email)."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Confirmation code requested successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VerificationResponse.class))
    )
    @ApiResponse(responseCode = "400", description = "Invalid request (missing userId)", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    public ResponseEntity<VerificationResponse> verificationProcess(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Verification push request containing user ID",
                    required = true,
                    content = @Content(schema = @Schema(implementation = VerificationPushRequest.class))
            )
            @RequestBody VerificationPushRequest verificationPushRequest
    ) {
        VerificationPushProcessCommand verificationPushProcessCommand = new VerificationPushProcessCommand(
                verificationPushRequest.getUserId()
        );
        return ResponseEntity.ok(new VerificationResponse(
                verificationPushProccessCommandHandler.handle(verificationPushProcessCommand)
        ));
    }
}