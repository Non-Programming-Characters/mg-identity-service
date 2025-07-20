package ru.solomka.identity.token;

import ru.solomka.identity.common.mapper.Mapper;

public class JpaRefreshTokenEntityRefreshTokenEntityMapper implements Mapper<JpaRefreshTokenEntity, RefreshTokenEntity> {
    @Override
    public RefreshTokenEntity mapToDomain(JpaRefreshTokenEntity infrastructureEntity) {
        return RefreshTokenEntity.builder()
                .id(infrastructureEntity.getId())
                .createdAt(infrastructureEntity.getCreatedAt())
                .build();
    }

    @Override
    public JpaRefreshTokenEntity mapToInfrastructure(RefreshTokenEntity domainEntity) {
        return JpaRefreshTokenEntity.builder()
                .id(domainEntity.getId())
                .createdAt(domainEntity.getCreatedAt())
                .build();
    }
}
