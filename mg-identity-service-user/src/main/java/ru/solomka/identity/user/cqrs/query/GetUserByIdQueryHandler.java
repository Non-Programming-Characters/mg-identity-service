package ru.solomka.identity.user.cqrs.query;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserService;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetUserByIdQueryHandler implements CommandHandler<GetUserByIdQuery, UserEntity> {

    @NonNull
    UserService userService;

    @Override
    public UserEntity handle(GetUserByIdQuery command) {
        return userService.findById(command.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id '%s' not found"));
    }
}