package ru.solomka.identity.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.EntityService;
import ru.solomka.identity.common.exception.EntityNotFoundException;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService extends EntityService<UserEntity> {

    @NonNull UserRepository userRepository;

    public UserService(@NonNull UserRepository repository) {
        super(repository);
        this.userRepository = repository;
    }

    public UserEntity getByLogin(@NonNull String login){
        return this.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("User with login '%s' not found".formatted(login)));
    }

    public UserEntity getByEmail(@NonNull String email){
        return this.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email '%s' not found".formatted(email)));
    }

    public UserEntity getByFirstName(@NonNull String firstName){
        return this.findByFirstName(firstName)
                .orElseThrow(() -> new EntityNotFoundException("User with name '%s' not found".formatted(firstName)));
    }

    public UserEntity getByLastName(@NonNull String lastName){
        return this.findByFirstName(lastName)
                .orElseThrow(() -> new EntityNotFoundException("User with lastname '%s' not found".formatted(lastName)));
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