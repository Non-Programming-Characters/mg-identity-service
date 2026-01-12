package ru.solomka.identity.token.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import ru.solomka.identity.token.TokenPair;
import ru.solomka.identity.token.cqrs.IssueTokenPairCommand;
import ru.solomka.identity.token.request.TokenRefreshRequest;
import ru.solomka.identity.token.response.TokenPairResponse;

@RestController
@RequestMapping("/api/v1/identity/security/token")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Token Management", description = "API for issuing and refreshing JWT tokens")
public class RefreshTokenRestController {

    @NonNull
    CommandHandler<IssueTokenPairCommand, TokenPair> issueTokenPairCommandHandler;

    @PostMapping(value = "/refresh", produces = "application/json")
    @Operation(
            summary = "Refresh access and refresh tokens",
            description = "Exchanges a valid refresh token for a new pair of access and refresh tokens."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully issued new token pair",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenPairResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Bad Request: Missing or malformed refresh token",
            content = @Content
    )
    @ApiResponse(
            responseCode = "401",
            description = "Unauthorized: Refresh token is invalid, expired, or revoked",
            content = @Content
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content
    )
    public ResponseEntity<TokenPairResponse> refreshAccessToken(@RequestBody TokenRefreshRequest tokenRefreshRequest) {
        TokenPair tokenPair = issueTokenPairCommandHandler.handle(
                new IssueTokenPairCommand(tokenRefreshRequest.getRefreshToken())
        );
        return ResponseEntity.ok(new TokenPairResponse(
                tokenPair.getAccessToken().getToken(),
                tokenPair.getRefreshToken().getToken()
        ));
    }
}