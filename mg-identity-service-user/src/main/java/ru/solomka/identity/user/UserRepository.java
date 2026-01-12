package ru.solomka.identity.user;

import ru.solomka.identity.common.EntityRepository;

import java.util.Optional;

public interface UserRepository extends EntityRepository<UserEntity> {
    Optional<UserEntity> findUserByLogin(String login);
    Optional<UserEntity> findUserByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);
}