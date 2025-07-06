package ru.solomka.identity.common.mapper;

public interface Mapper<I, D> {
    I mapToInfrastructure(D domainEntity);

    D mapToDomain(I infrastructureEntity);
}