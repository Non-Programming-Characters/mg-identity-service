package ru.solomka.identity.authentication.cqrs.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.authentication.cqrs.LogoutCommand;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.token.RefreshTokenService;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogoutCommandHandler implements CommandHandler<LogoutCommand, Boolean> {

    @NonNull RefreshTokenService refreshTokenService;

    @Override
    public Boolean handle(LogoutCommand command) {
        return refreshTokenService.deleteById(command.getUserId()) != null;
    }
}