package ru.solomka.identity.user;

import ru.solomka.identity.common.mapper.Mapper;

public class UserEntityJpaUserEntityMapper implements Mapper<JpaUserEntity, UserEntity> {

    @Override
    public JpaUserEntity mapToInfrastructure(UserEntity domainEntity) {
        return JpaUserEntity.builder()
                .id(domainEntity.getId())
                .login(domainEntity.getLogin())
                .passwordHash(domainEntity.getPasswordHash())
                .email(domainEntity.getEmail())
                .createdAt(domainEntity.getCreatedAt())
                .build();
    }

    @Override
    public UserEntity mapToDomain(JpaUserEntity infrastructureEntity) {
        return UserEntity.builder()
                .id(infrastructureEntity.getId())
                .login(infrastructureEntity.getLogin())
                .passwordHash(infrastructureEntity.getPasswordHash())
                .email(infrastructureEntity.getEmail())
                .createdAt(infrastructureEntity.getCreatedAt())
                .build();
    }
}