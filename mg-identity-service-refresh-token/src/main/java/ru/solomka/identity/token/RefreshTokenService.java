package ru.solomka.identity.token;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.EntityService;

import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenService extends EntityService<RefreshTokenEntity> {

    public RefreshTokenService(@NonNull RefreshTokenRepository repository) {
        super(repository);
    }

    @Override
    public RefreshTokenEntity create(RefreshTokenEntity entity) {
        entity.setCreatedAt(Instant.now());
        return repository.create(entity);
    }
}