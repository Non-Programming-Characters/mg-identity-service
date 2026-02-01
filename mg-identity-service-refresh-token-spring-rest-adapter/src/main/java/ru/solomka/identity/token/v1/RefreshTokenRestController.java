package ru.solomka.identity.token.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.common.exception.HttpExtractPayloadException;
import ru.solomka.identity.token.TokenPair;
import ru.solomka.identity.token.cqrs.IssueTokenPairCommand;
import ru.solomka.identity.token.response.TokenResponse;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/identity/public/security/token")
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
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))
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
    public ResponseEntity<TokenResponse> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length == 0) {
            throw new HttpExtractPayloadException("No cookies provided");
        }

        String refreshToken = Arrays.stream(request.getCookies()).toList().stream()
                .filter(cookie -> cookie.getName().equals("REFRESH_TOKEN"))
                .map(Cookie::getValue)
                .findAny().orElseThrow(() -> new HttpExtractPayloadException("No cookie found suitable for this operation"));

        TokenPair tokenPair = issueTokenPairCommandHandler.handle(
                new IssueTokenPairCommand(refreshToken)
        );

        long maxAgeSeconds = Duration.between(Instant.now(), tokenPair.getRefreshToken().getExpiredAt()).getSeconds();
        if (maxAgeSeconds <= 0) {
            throw new IllegalStateException("Refresh token already expired");
        }

        ResponseCookie refreshTokenCookie = ResponseCookie.from("REFRESH_TOKEN", tokenPair.getRefreshToken().getToken())
                .httpOnly(true)
                .sameSite("Lax")
                .path("/api/v1/identity")
                .maxAge(maxAgeSeconds)
                .build();

        response.addHeader("Set-Cookie", refreshTokenCookie.toString());

        return ResponseEntity.ok(new TokenResponse(
                tokenPair.getAccessToken().getToken()
        ));
    }
}