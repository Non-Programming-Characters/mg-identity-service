package ru.solomka.identity.common;

import java.util.Optional;
import java.util.UUID;

public interface EntityRepository<E extends Entity> {

    E create(E entity);
    E update(E entity);
    E deleteById(UUID id);

    boolean existsById(UUID id);

    Optional<E> findById(UUID id);
}
