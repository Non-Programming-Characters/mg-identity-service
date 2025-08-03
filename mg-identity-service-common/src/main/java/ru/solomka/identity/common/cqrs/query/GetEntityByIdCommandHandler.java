package ru.solomka.identity.common.cqrs.query;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.Entity;
import ru.solomka.identity.common.EntityService;
import ru.solomka.identity.common.cqrs.CommandHandler;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetEntityByIdCommandHandler<E extends Entity> implements CommandHandler<GetEntityByIdCommand, E> {

    @NonNull
    EntityService<E> entityService;

    @Override
    public E handle(GetEntityByIdCommand command) {
        return entityService.getById(command.getId());
    }
}
