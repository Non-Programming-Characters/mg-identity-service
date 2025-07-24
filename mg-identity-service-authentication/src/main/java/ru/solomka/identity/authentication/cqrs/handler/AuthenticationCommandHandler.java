package ru.solomka.identity.authentication.cqrs.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.authentication.AuthenticationService;
import ru.solomka.identity.authentication.cqrs.AuthenticationCommand;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.token.RefreshTokenService;
import ru.solomka.identity.token.TokenPair;
import ru.solomka.identity.token.TokenPairFactory;

import java.time.Duration;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationCommandHandler implements CommandHandler<AuthenticationCommand, TokenPair> {

    @NonNull AuthenticationService authenticationService;

    @NonNull TokenPairFactory tokenPairFactory;
    @NonNull RefreshTokenService refreshTokenService;
    @NonNull Duration accessTokenLifetime;
    @NonNull Duration refreshTokenLifetime;

    @Override
    public TokenPair handle(AuthenticationCommand command) {
        if(command.getLogin().isEmpty() || command.getPassword().isEmpty())
            throw new IllegalArgumentException("Login and password are required");

        PrincipalEntity principalEntity = authenticationService.authenticate(command.getLogin(), command.getPassword());

        if(refreshTokenService.existsById(principalEntity.getId()))
            refreshTokenService.deleteById(principalEntity.getId());

        return tokenPairFactory.create(principalEntity, accessTokenLifetime, refreshTokenLifetime);
    }
}