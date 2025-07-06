package ru.solomka.identity.user;

import ru.solomka.identity.common.mapper.Mapper;

public class UserEntityJpaUserEntityMapper implements Mapper<JpaUserEntity, UserEntity> {

    @Override
    public JpaUserEntity mapToInfrastructure(UserEntity domainEntity) {
        return JpaUserEntity.builder()
                .id(domainEntity.getId())
                .login(domainEntity.getLogin())
                .passwordHash(domainEntity.getPasswordHash())
                .phoneNumber(domainEntity.getPhoneNumber())
                .email(domainEntity.getEmail())
                .firstName(domainEntity.getFirstName())
                .lastName(domainEntity.getLastName())
                .bio(domainEntity.getBio())
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
                .phoneNumber(infrastructureEntity.getPhoneNumber())
                .email(infrastructureEntity.getEmail())
                .firstName(infrastructureEntity.getFirstName())
                .lastName(infrastructureEntity.getLastName())
                .bio(infrastructureEntity.getBio())
                .birthDate(infrastructureEntity.getBirthDate())
                .createdAt(infrastructureEntity.getCreatedAt())
                .build();
    }
}