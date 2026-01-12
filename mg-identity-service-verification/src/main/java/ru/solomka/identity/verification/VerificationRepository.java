package ru.solomka.identity.verification;

import ru.solomka.identity.common.EntityRepository;

import java.util.Optional;
import java.util.UUID;

public interface VerificationRepository extends EntityRepository<VerificationEntity> {

    void clearAllExpireVerifications();

    Optional<VerificationPair> findPayloadByReceiverId(UUID receiverId);

    Optional<VerificationEntity> findVerificationByReceiverId(UUID receiverId);

    boolean existsByReceiverId(UUID receiverId);
}