package ru.solomka.identity.user.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserService;
import ru.solomka.identity.user.cqrs.query.GetUserByEmailQuery;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GetUserByEmailQueryHandler implements CommandHandler<GetUserByEmailQuery, UserEntity> {

    @NonNull UserService userService;

    @Override
    public UserEntity handle(GetUserByEmailQuery command) {

        if(command.getEmail().isEmpty())
            throw new IllegalArgumentException("Argument 'email' cannot be empty");

        return userService.getByEmail(command.getEmail());
    }
}
