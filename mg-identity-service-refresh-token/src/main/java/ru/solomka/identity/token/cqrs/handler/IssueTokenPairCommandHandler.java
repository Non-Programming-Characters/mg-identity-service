package ru.solomka.identity.token.cqrs.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.token.*;
import ru.solomka.identity.token.cqrs.IssueTokenPairCommand;
import ru.solomka.identity.token.exception.TokenExpiredException;
import ru.solomka.identity.token.exception.TokenVerificationException;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserService;

import java.time.Duration;
import java.time.Instant;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IssueTokenPairCommandHandler implements CommandHandler<IssueTokenPairCommand, TokenPair> {

    @NonNull RefreshTokenService refreshTokenService;
    @NonNull TokenExtractor tokenExtractor;

    @NonNull UserService userService;

    @NonNull PrincipalService principalService;

    @NonNull TokenPairFactory tokenPairFactory;
    @NonNull Duration lifetimeAccessToken;
    @NonNull Duration lifetimeRefreshToken;

    @Override
    public TokenPair handle(IssueTokenPairCommand command) {
        if(command.getRefreshToken().isEmpty())
            throw new IllegalArgumentException("Refresh token is empty");

        TokenEntity tokenEntity = tokenExtractor.extract(command.getRefreshToken());

        if(Instant.now().isAfter(tokenEntity.getExpiredAt()))
            throw new TokenExpiredException("Token is expired");

        if(!refreshTokenService.existsById(tokenEntity.getId()))
            throw new TokenVerificationException("Token verification failed");

        UserEntity userEntity = userService.getById(tokenEntity.getUserId());

        principalService.setPrincipal(PrincipalEntity.builder().id(tokenEntity.getUserId()).username(userEntity.getLogin()).build());

        refreshTokenService.deleteById(tokenEntity.getId());

        refreshTokenService.create(RefreshTokenEntity.builder().id(userEntity.getId()).createdAt(Instant.now()).build());

        return tokenPairFactory.create(
                principalService.getPrincipal(),
                lifetimeAccessToken,
                lifetimeRefreshToken
        );
    }
}