package ru.solomka.identity.user;

import ru.solomka.identity.common.mapper.Mapper;
import ru.solomka.identity.user.response.UserEntityResponse;

public class UserEntityResponseUserEntityMapper implements Mapper<UserEntityResponse, UserEntity> {


    @Override
    public UserEntityResponse mapToInfrastructure(UserEntity domainEntity) {
        return UserEntityResponse.builder()
                .id(domainEntity.getId())
                .login(domainEntity.getLogin())
                .email(domainEntity.getEmail())
                .firstName(domainEntity.getFirstName())
                .lastName(domainEntity.getLastName())
                .bio(domainEntity.getBio())
                .birthDate(domainEntity.getBirthDate())
                .createdAt(domainEntity.getCreatedAt())
                .build();
    }

    @Override
    public UserEntity mapToDomain(UserEntityResponse infrastructureEntity) {
        throw new UnsupportedOperationException();
    }
}