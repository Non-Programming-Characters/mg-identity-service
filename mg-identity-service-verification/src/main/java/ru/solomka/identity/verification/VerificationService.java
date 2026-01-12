package ru.solomka.identity.verification;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.EntityNotificationService;
import ru.solomka.identity.common.EntityService;
import ru.solomka.identity.common.exception.EntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerificationService extends EntityService<VerificationEntity> {

    @NonNull VerificationRepository verificationRepository;
    @NonNull EntityNotificationService<VerificationEntity> notificationService;

    public VerificationService(@NonNull VerificationRepository repository,
                               @NonNull EntityNotificationService<VerificationEntity> notificationService) {
        super(repository);
        this.verificationRepository = repository;
        this.notificationService = notificationService;
    }

    @Override
    public VerificationEntity create(VerificationEntity entity) {
        VerificationEntity createdNotifyCreate = super.create(entity);
        notificationService.notifyCreated(createdNotifyCreate);
        return createdNotifyCreate;
    }

    public void clearAllExpireVerifications() {
        this.verificationRepository.clearAllExpireVerifications();
    }

    public VerificationEntity getVerificationByReceiverId(UUID receiverId) {
        return this.verificationRepository.findVerificationByReceiverId(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Verification with receiver '%s' not found".formatted(receiverId)));
    }

    public VerificationPair getPayloadByReceiverId(UUID receiverId) {
        return this.verificationRepository.findPayloadByReceiverId(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Verification payload with receiver '%s' not found".formatted(receiverId)));
    }

    public Optional<VerificationEntity> findVerificationByReceiverId(UUID receiverId) {
        return this.verificationRepository.findVerificationByReceiverId(receiverId);
    }

    public Optional<VerificationPair> findPayloadByReceiverId(UUID receiverId) {
        return this.verificationRepository.findPayloadByReceiverId(receiverId);
    }

    public boolean existsByReceiverId(UUID receiverId) {
        return this.verificationRepository.existsByReceiverId(receiverId);
    }
}