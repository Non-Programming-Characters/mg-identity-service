package ru.solomka.identity.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.BaseJpaRepositoryAdapter;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JpaUserEntityRepositoryAdapter extends BaseJpaRepositoryAdapter<JpaUserEntity, UserEntity> implements UserRepository {

    @NonNull JpaUserRepository jpaUserRepository;
    @NonNull UserEntityJpaUserEntityMapper userEntityJpaUserEntityMapper;

    public JpaUserEntityRepositoryAdapter(@NonNull JpaUserRepository repository,
                                          @NonNull UserEntityJpaUserEntityMapper mapper) {
        super(repository, mapper);
        this.jpaUserRepository = repository;
        this.userEntityJpaUserEntityMapper = mapper;
    }

    @Override
    public Optional<UserEntity> findByLogin(String login) {
        return jpaUserRepository.findByLogin(login).map(userEntityJpaUserEntityMapper::mapToDomain);
    }

    @Override
    public Optional<UserEntity> findByFirstName(String firstName) {
        return jpaUserRepository.findByFirstName(firstName).map(userEntityJpaUserEntityMapper::mapToDomain);
    }

    @Override
    public Optional<UserEntity> findByLastName(String lastName) {
        return jpaUserRepository.findByFirstName(lastName).map(userEntityJpaUserEntityMapper::mapToDomain);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return jpaUserRepository.findByFirstName(email).map(userEntityJpaUserEntityMapper::mapToDomain);
    }
}