package ru.solomka.identity.test.token;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.token.RefreshTokenEntity;
import ru.solomka.identity.token.RefreshTokenRepository;
import ru.solomka.identity.token.RefreshTokenService;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RefreshTokenTest {

    @Mock
    RefreshTokenRepository refreshTokenRepository;

    private RefreshTokenService refreshTokenService;

    private static final UUID TOKEN_UUID  = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        refreshTokenService = new RefreshTokenService(refreshTokenRepository);
    }

    @Test
    void shouldReturnRefreshTokenWhenFindById() {
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                .id(TOKEN_UUID)
                .createdAt(Instant.now())
                .build();

        Mockito.when(refreshTokenRepository.findById(TOKEN_UUID)).thenReturn(Optional.of(refreshTokenEntity));
    }

    @Test
    void shouldThrowWhenFindWithInvalidId() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> refreshTokenService.getById(UUID.randomUUID()));
    }
}