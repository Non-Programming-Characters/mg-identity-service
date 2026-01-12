package ru.solomka.identity.test.token;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.token.RefreshTokenEntity;
import ru.solomka.identity.token.RefreshTokenRepository;
import ru.solomka.identity.token.RefreshTokenService;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RefreshTokenTest {

    @Mock private RefreshTokenRepository refreshTokenRepository;

    private RefreshTokenService refreshTokenService;

    @BeforeEach
    void setUp() {
        refreshTokenService = new RefreshTokenService(refreshTokenRepository);
    }

    @Nested
    @DisplayName("Получение refresh-токена по ID")
    class GetById {

        @Test
        @DisplayName("Возвращает токен, если он существует")
        void shouldReturnRefreshTokenWhenFound() {
            UUID tokenId = UUID.randomUUID();
            RefreshTokenEntity expectedToken = RefreshTokenEntity.builder()
                    .id(tokenId)
                    .createdAt(Instant.now())
                    .build();

            given(refreshTokenRepository.findById(tokenId)).willReturn(Optional.of(expectedToken));

            RefreshTokenEntity result = refreshTokenService.getById(tokenId);

            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(expectedToken.getId());
        }

        @Test
        @DisplayName("Выбрасывает EntityNotFoundException, если токен не найден")
        void shouldThrowEntityNotFoundExceptionWhenTokenNotFound() {
            UUID nonExistentId = UUID.randomUUID();
            given(refreshTokenRepository.findById(nonExistentId)).willReturn(Optional.empty());

            assertThatThrownBy(() -> refreshTokenService.getById(nonExistentId))
                    .isInstanceOf(EntityNotFoundException.class);
        }
    }
}