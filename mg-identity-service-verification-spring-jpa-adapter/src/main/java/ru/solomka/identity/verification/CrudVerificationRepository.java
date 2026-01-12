package ru.solomka.identity.verification;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.solomka.identity.common.BaseCrudRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface CrudVerificationRepository extends BaseCrudRepository<JpaVerificationEntity> {

    @Modifying
    @Query("delete from JpaVerificationEntity jve where jve.createdAt <= :now")
    void clearAllExpireVerifications(Instant now);

    @Query("select jve from JpaVerificationEntity jve where jve.receiverId = :receiverId")
    Optional<JpaVerificationEntity> getVerificationByReceiverId(UUID receiverId);

    @Query("select count(jve) > 0 from JpaVerificationEntity jve where jve.receiverId = :receiverId")
    boolean existsByReceiverId(UUID receiverId);
}