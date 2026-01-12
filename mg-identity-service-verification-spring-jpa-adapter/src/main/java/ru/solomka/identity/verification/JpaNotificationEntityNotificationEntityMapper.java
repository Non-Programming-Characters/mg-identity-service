package ru.solomka.identity.verification;

import ru.solomka.identity.common.mapper.Mapper;

public class JpaNotificationEntityNotificationEntityMapper implements Mapper<JpaVerificationEntity, VerificationEntity> {

    @Override
    public JpaVerificationEntity mapToInfrastructure(VerificationEntity domainEntity) {
        return JpaVerificationEntity.builder()
                .id(domainEntity.getId())
                .receiverId(domainEntity.getReceiverId())
                .payload(domainEntity.getPayload())
                .type(domainEntity.getType())
                .expiredAt(domainEntity.getExpiredAt())
                .createdAt(domainEntity.getCreatedAt())
                .build();
    }

    @Override
    public VerificationEntity mapToDomain(JpaVerificationEntity infrastructureEntity) {
        return VerificationEntity.builder()
                .id(infrastructureEntity.getId())
                .receiverId(infrastructureEntity.getReceiverId())
                .payload(infrastructureEntity.getPayload())
                .type(infrastructureEntity.getType())
                .expiredAt(infrastructureEntity.getExpiredAt())
                .createdAt(infrastructureEntity.getCreatedAt())
                .build();
    }
}