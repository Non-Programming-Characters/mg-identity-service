package ru.solomka.identity.user;

import ru.solomka.identity.common.EntityRepository;

import java.util.Optional;

public interface UserRepository extends EntityRepository<UserEntity> {
    UserEntity findByLogin(String login);
    UserEntity findByEmail(String email);
}