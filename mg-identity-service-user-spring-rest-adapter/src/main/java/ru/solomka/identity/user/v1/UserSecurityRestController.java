package ru.solomka.identity.user.v1;

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
import ru.solomka.identity.authentication.cqrs.AuthenticationCommand;
import ru.solomka.identity.authentication.cqrs.RegistrationCommand;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.token.TokenPair;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.request.AuthenticationRequest;
import ru.solomka.identity.user.request.RegistrationRequest;
import ru.solomka.identity.user.response.security.AuthenticationResponse;
import ru.solomka.identity.user.response.security.RegistrationResponse;

@RestController
@RequestMapping("/api/v1/identity/public/security/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Authentication & Registration", description = "API for user sign-in and sign-up")
public class UserSecurityRestController {

    @NonNull CommandHandler<RegistrationCommand, UserEntity> registrationCommandHandler;
    @NonNull CommandHandler<AuthenticationCommand, TokenPair> authenticationCommandHandler;

    @PostMapping(value = "/signin", produces = "application/json")
    @Operation(
            summary = "Authenticate user and issue tokens",
            description = "Authenticates a user by login and password, and returns a pair of access and refresh JWT tokens."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Authentication successful",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))
    )
    @ApiResponse(responseCode = "400", description = "Bad Request: Missing or empty credentials", content = @Content)
    @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid login or password", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden: Account is not verified or blocked", content = @Content)
    public ResponseEntity<AuthenticationResponse> signinUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User credentials for authentication",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AuthenticationRequest.class))
            )
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        TokenPair tokenPair = authenticationCommandHandler.handle(new AuthenticationCommand(
                authenticationRequest.getLogin(),
                authenticationRequest.getPassword()
        ));
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .accessToken(tokenPair.getAccessToken().getToken())
                .refreshToken(tokenPair.getRefreshToken().getToken())
                .build());
    }

    @PostMapping(value = "/signup", produces = "application/json")
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with the provided credentials. The account may require email verification."
    )
    @ApiResponse(
            responseCode = "200",
            description = "User registered successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistrationResponse.class))
    )
    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid or missing fields (e.g., malformed email, weak password)", content = @Content)
    @ApiResponse(responseCode = "409", description = "Conflict: Login or email already exists", content = @Content)
    public ResponseEntity<RegistrationResponse> signupUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New user registration details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RegistrationRequest.class))
            )
            @RequestBody RegistrationRequest registrationRequest
    ) {
        UserEntity userEntity = registrationCommandHandler.handle(new RegistrationCommand(
                registrationRequest.getLogin(),
                registrationRequest.getPassword(),
                registrationRequest.getEmail()
        ));

        return ResponseEntity.ok(RegistrationResponse.builder()
                .login(userEntity.getLogin())
                .email(userEntity.getEmail())
                .build());
    }
}