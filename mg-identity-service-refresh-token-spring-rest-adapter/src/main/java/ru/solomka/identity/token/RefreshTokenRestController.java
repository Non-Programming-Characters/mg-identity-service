package ru.solomka.identity.token;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.identity.token.request.TokenRefreshRequest;
import ru.solomka.identity.token.response.TokenPairResponse;

@RestController("/security/token/pair")
public class RefreshTokenRestController {

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
        //todo
        return ResponseEntity.ok(new TokenPairResponse("", ""));
    }
}
