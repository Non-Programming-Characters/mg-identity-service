package ru.solomka.identity.token;

import lombok.NonNull;
import ru.solomka.identity.common.BaseCrudRepository;
import ru.solomka.identity.common.BaseJpaRepositoryAdapter;
import ru.solomka.identity.common.mapper.Mapper;

public class JpaRefreshTokenRepositoryAdapter extends BaseJpaRepositoryAdapter<JpaRefreshTokenEntity, RefreshTokenEntity> implements RefreshTokenRepository {
    public JpaRefreshTokenRepositoryAdapter(@NonNull BaseCrudRepository<JpaRefreshTokenEntity> repository,
                                            @NonNull Mapper<JpaRefreshTokenEntity, RefreshTokenEntity> mapper) {
        super(repository, mapper);
    }
}