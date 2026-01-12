package ru.solomka.identity.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.BaseJpaRepositoryAdapter;

import java.util.Optional;

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
    public Optional<UserEntity> findUserByLogin(String login) {
        return this.crudUserRepository.getUserByLogin(login).map(userEntityJpaUserEntityMapper::mapToDomain);
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        return this.crudUserRepository.getUserByEmail(email).map(userEntityJpaUserEntityMapper::mapToDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.crudUserRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByLogin(String email) {
        return this.crudUserRepository.existsByLogin(email);
    }
}