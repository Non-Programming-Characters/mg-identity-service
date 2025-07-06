package ru.solomka.identity.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.BaseJpaRepositoryAdapter;
import ru.solomka.identity.common.exception.EntityNotFoundException;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JpaUserEntityRepositoryAdapter extends BaseJpaRepositoryAdapter<JpaUserEntity, UserEntity> implements UserRepository {

    @NonNull JpaUserRepository jpaUserRepository;

    public JpaUserEntityRepositoryAdapter(@NonNull JpaUserRepository repository,
                                          @NonNull UserEntityJpaUserEntityMapper mapper) {
        super(repository, mapper);
        this.jpaUserRepository = repository;
    }

    @Override
    public UserEntity findByLogin(String login) {
        return jpaUserRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("User with login '%s' not found".formatted(login)));
    }

    @Override
    public UserEntity findByFirstName(String firstName) {
        return jpaUserRepository.findByFirstName(firstName)
                .orElseThrow(() -> new EntityNotFoundException("User with first name '%s' not found".formatted(firstName)));
    }

    @Override
    public UserEntity findByLastName(String lastName) {
        return jpaUserRepository.findByFirstName(lastName)
                .orElseThrow(() -> new EntityNotFoundException("User with last name '%s' not found".formatted(lastName)));
    }

    @Override
    public UserEntity findByEmail(String email) {
        return jpaUserRepository.findByFirstName(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email '%s' not found".formatted(email)));
    }
}