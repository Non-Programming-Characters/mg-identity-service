package ru.solomka.identity.token.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.token.TokenPair;
import ru.solomka.identity.token.cqrs.IssueTokenPairCommand;
import ru.solomka.identity.token.request.TokenRefreshRequest;
import ru.solomka.identity.token.response.TokenPairResponse;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController("/v1/security/token")
public class RefreshTokenRestController {

    @NonNull CommandHandler<IssueTokenPairCommand, TokenPair> issueTokenPairCommandHandler;

    @Operation(
            summary = "Refresh access token",
            method = "POST",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenRefreshRequest.class)),
                    useParameterTypeSchema = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Returns updated token pair (Access and Refresh tokens)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenPairResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403", description = "Exception: Token is not valid (Expired, Structure and etc.)",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping(value = "/refresh", produces = "application/json")
    public ResponseEntity<TokenPairResponse> refreshAccessToken(@RequestBody TokenRefreshRequest tokenRefreshRequest) {
        TokenPair tokenPair = issueTokenPairCommandHandler.handle(new IssueTokenPairCommand(tokenRefreshRequest.getRefreshToken()));
        return ResponseEntity.ok(new TokenPairResponse(
                tokenPair.getAccessToken().getToken(),
                tokenPair.getRefreshToken().getToken()
        ));
    }
}