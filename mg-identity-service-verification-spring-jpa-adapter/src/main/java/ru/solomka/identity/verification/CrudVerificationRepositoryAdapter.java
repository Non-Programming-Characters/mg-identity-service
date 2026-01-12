package ru.solomka.identity.verification;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.BaseJpaRepositoryAdapter;
import ru.solomka.identity.common.mapper.Mapper;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CrudVerificationRepositoryAdapter extends BaseJpaRepositoryAdapter<JpaVerificationEntity, VerificationEntity> implements VerificationRepository {

    @NonNull CrudVerificationRepository crudVerificationRepository;
    @NonNull Mapper<JpaVerificationEntity, VerificationEntity> mapper;

    public CrudVerificationRepositoryAdapter(@NonNull CrudVerificationRepository repository,
                                             @NonNull Mapper<JpaVerificationEntity, VerificationEntity> mapper) {
        super(repository, mapper);
        this.crudVerificationRepository = repository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public void clearAllExpireVerifications() {
        this.crudVerificationRepository.clearAllExpireVerifications(Instant.now());
    }

    @Override
    public Optional<VerificationPair> findPayloadByReceiverId(UUID receiverId) {
        return this.crudVerificationRepository.getVerificationByReceiverId(receiverId)
                .map(verification -> new VerificationPair(verification.getPayload(), verification.getType()));
    }

    @Override
    public Optional<VerificationEntity> findVerificationByReceiverId(UUID receiverId) {
        return this.crudVerificationRepository.getVerificationByReceiverId(receiverId).map(mapper::mapToDomain);
    }

    @Override
    public boolean existsByReceiverId(UUID receiverId) {
        return this.crudVerificationRepository.existsByReceiverId(receiverId);
    }
}
