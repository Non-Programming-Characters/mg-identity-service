package ru.solomka.identity.user;

import ru.solomka.identity.common.mapper.Mapper;
import ru.solomka.identity.user.response.UserSearchResponse;

public class UserSearchResponseUserEntityMapper implements Mapper<UserSearchResponse, UserEntity> {

    @Override
    public UserSearchResponse mapToInfrastructure(UserEntity domainEntity) {
        return UserSearchResponse.builder()
                .id(domainEntity.getId())
                .login(domainEntity.getLogin())
                .email(domainEntity.getEmail())
                .firstName(domainEntity.getFirstName())
                .lastName(domainEntity.getLastName())
                .createdAt(domainEntity.getCreatedAt())
                .build();
    }

    @Override
    public UserEntity mapToDomain(UserSearchResponse infrastructureEntity) {
        throw new UnsupportedOperationException();
    }
}