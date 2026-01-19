package ru.solomka.identity.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.EntityNotificationService;
import ru.solomka.identity.common.EntityService;
import ru.solomka.identity.common.exception.EntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService extends EntityService<UserEntity> {

    @NonNull UserRepository userRepository;
    @NonNull EntityNotificationService<UserEntity> notificationService;

    public UserService(@NonNull UserRepository repository,
                       @NonNull EntityNotificationService<UserEntity> notificationService) {
        super(repository);
        this.userRepository = repository;
        this.notificationService = notificationService;
    }

    @Override
    public UserEntity update(UserEntity entity) {
        UserEntity updatedUserEntity = super.update(entity);
        notificationService.notifyUpdated(updatedUserEntity);
        return updatedUserEntity;
    }

    @Override
    public UserEntity deleteById(UUID id) {
        UserEntity deletedUserEntity = super.deleteById(id);
        notificationService.notifyDeleted(deletedUserEntity);
        return deletedUserEntity;
    }

    public UserEntity getUserByLogin(String login) {
        return this.userRepository.findUserByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("User with login '%s' not found".formatted(login)));
    }

    public UserEntity getUserByEmail(String email) {
        return this.userRepository.findUserByLogin(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email '%s' not found".formatted(email)));
    }

    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public boolean existsByLogin(String email) {
        return this.userRepository.existsByLogin(email);
    }

    public Optional<UserEntity> findUserByLogin(@NonNull String login) {
        return userRepository.findUserByLogin(login);
    }

    public Optional<UserEntity> findUserByEmail(@NonNull String email){
        return userRepository.findUserByEmail(email);
    }
}