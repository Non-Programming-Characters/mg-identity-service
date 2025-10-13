package ru.solomka.identity.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.BaseJpaRepositoryAdapter;
import ru.solomka.identity.common.exception.EntityNotFoundException;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JpaUserEntityRepositoryAdapter extends BaseJpaRepositoryAdapter<JpaUserEntity, UserEntity> implements UserRepository {

    @NonNull CrudUserRepository crudUserRepository;
    @NonNull UserEntityJpaUserEntityMapper userEntityJpaUserEntityMapper;

    public JpaUserEntityRepositoryAdapter(@NonNull CrudUserRepository repository,
                                          @NonNull UserEntityJpaUserEntityMapper mapper) {
        super(repository, mapper);
        this.crudUserRepository = repository;
        this.userEntityJpaUserEntityMapper = mapper;
    }

    @Override
    public UserEntity findByLogin(String login) {
        return crudUserRepository.findByLogin(login).map(userEntityJpaUserEntityMapper::mapToDomain)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with login '%s' not found", login)));
    }

    @Override
    public UserEntity findByEmail(String email) {
        return crudUserRepository.findByEmail(email).map(userEntityJpaUserEntityMapper::mapToDomain)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with email '%s' not found", email)));
    }
}