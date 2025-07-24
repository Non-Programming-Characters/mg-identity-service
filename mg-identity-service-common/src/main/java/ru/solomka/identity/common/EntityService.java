package ru.solomka.identity.common;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.exception.EntityNotFoundException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class EntityService<E extends Entity> {

    @NonNull EntityRepository<E> repository;

    public E create(E entity) {
        entity.setId(UUID.randomUUID());
        entity.setCreatedAt(Instant.now());
        return repository.create(entity);
    }

    public E update(E entity) {
        return repository.update(entity);
    }

    public E deleteById(UUID id) {
        if(!this.existsById(id))
            throw new EntityNotFoundException("Entity with id '%s' not found".formatted(id));

        return repository.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    public Optional<E> findById(UUID id) {
        return repository.findById(id);
    }

    public E getById(UUID id) {
        return this.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with id '%s' not found".formatted(id)));
    }
}