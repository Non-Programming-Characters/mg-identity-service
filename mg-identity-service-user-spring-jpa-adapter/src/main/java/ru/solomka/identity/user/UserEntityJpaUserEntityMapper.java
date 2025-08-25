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
                .firstName(domainEntity.getFirstName())
                .lastName(domainEntity.getLastName())
                .birthDate(domainEntity.getBirthDate())
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
                .firstName(infrastructureEntity.getFirstName())
                .lastName(infrastructureEntity.getLastName())
                .birthDate(infrastructureEntity.getBirthDate())
                .createdAt(infrastructureEntity.getCreatedAt())
                .build();
    }
}