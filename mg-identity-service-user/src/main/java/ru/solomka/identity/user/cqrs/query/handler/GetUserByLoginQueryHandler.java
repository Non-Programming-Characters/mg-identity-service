package ru.solomka.identity.user.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserService;
import ru.solomka.identity.user.cqrs.query.GetUserByLoginQuery;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GetUserByLoginQueryHandler implements CommandHandler<GetUserByLoginQuery, UserEntity> {

    @NonNull UserService userService;

    @Override
    public UserEntity handle(GetUserByLoginQuery command) {

        if(command.getLogin().isEmpty())
            throw new IllegalArgumentException("Argument 'login' cannot be empty");

        return userService.getByLogin(command.getLogin());
    }
}
