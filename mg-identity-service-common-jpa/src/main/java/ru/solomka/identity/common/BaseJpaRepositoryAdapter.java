package ru.solomka.identity.common;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.common.mapper.Mapper;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class BaseJpaRepositoryAdapter<I, E extends Entity> implements EntityRepository<E> {

    @NonNull BaseCrudRepository<I> repository;

    @NonNull Mapper<I, E> mapper;

    public E create(E entity) {
        I infrastructureEntity = mapper.mapToInfrastructure(entity);
        infrastructureEntity = repository.save(infrastructureEntity);
        return mapper.mapToDomain(infrastructureEntity);
    }

    public E update(E entity) {
        I infrastructureEntity = mapper.mapToInfrastructure(entity);
        infrastructureEntity = repository.save(infrastructureEntity);
        return mapper.mapToDomain(infrastructureEntity);
    }

    public E deleteById(UUID id) {
        E toDeleteSearchedEntity = this.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with id '%s' not found".formatted(id)));

        repository.deleteById(toDeleteSearchedEntity.getId());

        return toDeleteSearchedEntity;
    }

    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    public Optional<E> findById(UUID id) {
        return repository.findById(id).map(mapper::mapToDomain);
    }
}