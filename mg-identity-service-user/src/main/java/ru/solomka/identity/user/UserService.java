package ru.solomka.identity.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.EntityService;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService extends EntityService<UserEntity> {

    @NonNull UserRepository userRepository;

    public UserService(@NonNull UserRepository repository) {
        super(repository);
        this.userRepository = repository;
    }

    public UserEntity findByLogin(@NonNull String login) {
        return userRepository.findByLogin(login);
    }

    public UserEntity findByEmail(@NonNull String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity findByFirstName(@NonNull String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public UserEntity findByLastName(@NonNull String lastName) {
        return userRepository.findByLastName(lastName);
    }
}