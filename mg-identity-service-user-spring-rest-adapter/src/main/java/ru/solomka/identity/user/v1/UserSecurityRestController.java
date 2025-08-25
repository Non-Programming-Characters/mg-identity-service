package ru.solomka.identity.user.v1;

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
@RequestMapping("/v1/security/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSecurityRestController {

    @NonNull CommandHandler<RegistrationCommand, UserEntity> registrationCommandHandler;
    @NonNull CommandHandler<AuthenticationCommand, TokenPair> authenticationCommandHandler;

    @PostMapping(value = "/signin", produces = "application/json")
    public ResponseEntity<AuthenticationResponse> signinUser(@RequestBody AuthenticationRequest authenticationRequest) {
        TokenPair tokenPair = authenticationCommandHandler.handle(new AuthenticationCommand(
                authenticationRequest.getLogin(), authenticationRequest.getPassword()
        ));
        return ResponseEntity.ok(
                AuthenticationResponse.builder()
                .accessToken(tokenPair.getAccessToken().getToken())
                .refreshToken(tokenPair.getRefreshToken().getToken())
                .build()
        );
    }

    @PostMapping(value = "/signup", produces = "application/json")
    public ResponseEntity<RegistrationResponse> signupUser(@RequestBody RegistrationRequest registrationRequest) {
        UserEntity userEntity = registrationCommandHandler.handle(new RegistrationCommand(
                registrationRequest.getLogin(),
                registrationRequest.getPassword(),
                registrationRequest.getEmail(),
                registrationRequest.getFirstName(),
                registrationRequest.getLastName(),
                registrationRequest.getBirthDate())
        );

        return ResponseEntity.ok(RegistrationResponse.builder()
                .login(userEntity.getLogin())
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .build()
        );
    }
}