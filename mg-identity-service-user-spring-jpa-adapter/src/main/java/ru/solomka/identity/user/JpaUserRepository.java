package ru.solomka.identity.user;

import ru.solomka.identity.common.BaseCrudRepository;

import java.util.Optional;

public interface JpaUserRepository extends BaseCrudRepository<JpaUserEntity> {
    Optional<JpaUserEntity> findByLogin(String login);
    Optional<JpaUserEntity> findByFirstName(String firstName);
    Optional<JpaUserEntity> findByLastName(String lastName);
    Optional<JpaUserEntity> findByEmail(String email);
}