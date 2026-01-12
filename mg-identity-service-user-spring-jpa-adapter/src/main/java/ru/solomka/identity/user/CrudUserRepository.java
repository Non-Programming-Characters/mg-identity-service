package ru.solomka.identity.user;

import org.springframework.data.jpa.repository.Query;
import ru.solomka.identity.common.BaseCrudRepository;

import java.util.Optional;

public interface CrudUserRepository extends BaseCrudRepository<JpaUserEntity> {

    @Query("select jue from JpaUserEntity jue where jue.login = :login")
    Optional<JpaUserEntity> getUserByLogin(String login);

    @Query("select jue from JpaUserEntity jue where jue.email = :email")
    Optional<JpaUserEntity> getUserByEmail(String email);

    @Query("select count(jue) > 0 from JpaUserEntity jue where jue.email = :email")
    boolean existsByEmail(String email);

    @Query("select count(jue) > 0 from JpaUserEntity jue where jue.login = :login")
    boolean existsByLogin(String login);
}