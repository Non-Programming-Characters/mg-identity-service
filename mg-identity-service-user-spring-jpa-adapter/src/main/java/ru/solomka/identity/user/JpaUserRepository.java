package ru.solomka.identity.user;

import ru.solomka.identity.common.BaseCrudRepository;

import java.util.Optional;

public interface JpaUserRepository extends BaseCrudRepository<JpaUserEntity> {
    Optional<UserEntity> findByLogin(String login);
    Optional<UserEntity> findByFirstName(String firstName);
    Optional<UserEntity> findByLastName(String lastName);
    Optional<UserEntity> findByEmail(String email);
}