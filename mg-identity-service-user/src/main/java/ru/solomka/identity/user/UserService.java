package ru.solomka.identity.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.EntityService;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService extends EntityService<UserEntity> {

    @NonNull UserRepository userRepository;

    public UserService(@NonNull UserRepository repository) {
        super(repository);
        this.userRepository = repository;
    }

    public Optional<UserEntity> findByLogin(@NonNull String login) {
        return userRepository.findByLogin(login);
    }

    public Optional<UserEntity> findByEmail(@NonNull String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserEntity> findByFirstName(@NonNull String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public Optional<UserEntity> findByLastName(@NonNull String lastName) {
        return userRepository.findByLastName(lastName);
    }
}