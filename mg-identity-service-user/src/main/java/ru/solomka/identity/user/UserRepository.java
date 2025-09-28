package ru.solomka.identity.user;

import ru.solomka.identity.common.EntityRepository;

import java.util.Optional;

public interface UserRepository extends EntityRepository<UserEntity> {
    Optional<UserEntity> findByLogin(String login);
    Optional<UserEntity> findByEmail(String email);
}