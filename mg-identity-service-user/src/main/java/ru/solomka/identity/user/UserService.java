package ru.solomka.identity.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.EntityNotificationService;
import ru.solomka.identity.common.EntityService;
import ru.solomka.identity.common.exception.EntityNotFoundException;

import java.time.Instant;
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
    public UserEntity create(UserEntity entity) {
        entity.setCreatedAt(Instant.now());
        UserEntity createdUserEntity = super.create(entity);
        notificationService.notifyCreated(createdUserEntity);
        return createdUserEntity;
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

    public UserEntity getByLogin(@NonNull String login){
        return this.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("User with login '%s' not found".formatted(login)));
    }

    public UserEntity getByEmail(@NonNull String email){
        return this.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email '%s' not found".formatted(email)));
    }

    public Optional<UserEntity> findByLogin(@NonNull String login) {
        return userRepository.findByLogin(login);
    }

    public Optional<UserEntity> findByEmail(@NonNull String email) {
        return userRepository.findByEmail(email);
    }
}