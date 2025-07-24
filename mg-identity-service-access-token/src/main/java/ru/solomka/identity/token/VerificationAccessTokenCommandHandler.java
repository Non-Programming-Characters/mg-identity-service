package ru.solomka.identity.token;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.cqrs.CommandHandler;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerificationAccessTokenCommandHandler implements CommandHandler<VerificationAccessTokenCommand, TokenEntity> {

    @NonNull TokenExtractor tokenExtractor;

    @Override
    public TokenEntity handle(VerificationAccessTokenCommand command) {
        if(command.getAccessToken().isEmpty())
            throw new IllegalArgumentException("Token is empty");

        return tokenExtractor.extract(command.getAccessToken());
    }
}