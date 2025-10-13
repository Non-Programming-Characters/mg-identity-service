package ru.solomka.identity.user;

import ru.solomka.identity.common.BaseCrudRepository;

import java.util.Optional;

public interface CrudUserRepository extends BaseCrudRepository<JpaUserEntity> {
    Optional<JpaUserEntity> findByLogin(String login);
    Optional<JpaUserEntity> findByEmail(String email);
}