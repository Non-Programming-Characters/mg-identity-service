package ru.solomka.identity.test.token;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.token.NimbusTokenExtractorAdapter;
import ru.solomka.identity.token.NimbusTokenFactoryAdapter;
import ru.solomka.identity.token.TokenEntity;
import ru.solomka.identity.token.TokenType;
import ru.solomka.identity.token.exception.TokenException;
import ru.solomka.identity.token.exception.TokenExpiredException;
import ru.solomka.identity.token.exception.TokenParseException;
import ru.solomka.identity.token.exception.TokenVerificationException;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NimbusTokenFactoryExtractorTest {

    private NimbusTokenFactoryAdapter tokenFactory;
    private NimbusTokenExtractorAdapter tokenExtractor;
    private PrincipalEntity principal;

    @BeforeEach
    void setUp() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        JWSSigner jwsSigner = new RSASSASigner(keyPair.getPrivate());
        JWSVerifier jwsVerifier = new RSASSAVerifier((RSAPublicKey) keyPair.getPublic());
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).build();

        tokenFactory = new NimbusTokenFactoryAdapter(jwsSigner, jwsHeader);
        tokenExtractor = new NimbusTokenExtractorAdapter(jwsVerifier);

        principal = new PrincipalEntity(UUID.randomUUID(), "testuser");
    }

    @Nested
    @DisplayName("Создание и извлечение токена")
    class CreateAndExtractToken {

        @Test
        @DisplayName("Успешно создаёт и извлекает валидный токен")
        void shouldCreateAndExtractValidToken() {
            Duration ttl = Duration.ofHours(1);
            TokenType tokenType = TokenType.ACCESS_TOKEN;

            TokenEntity token = tokenFactory.create(principal, ttl, tokenType);
            TokenEntity extracted = tokenExtractor.extract("Bearer " + token.getToken());

            assertThat(token).isNotNull();
            assertThat(token.getUserId()).isEqualTo(principal.getId());
            assertThat(token.getExpiredAt()).isAfter(java.time.Instant.now());
            assertThat(token.getTokenType()).isEqualTo(tokenType);

            assertThat(extracted.getId()).isEqualTo(token.getId());
            assertThat(extracted.getUserId()).isEqualTo(token.getUserId());
            assertThat(extracted.getTokenType()).isEqualTo(token.getTokenType());
        }
    }

    @Nested
    @DisplayName("Обработка ошибок при извлечении токена")
    class TokenExtractionErrors {

        @Test
        @DisplayName("Выбрасывает TokenExpiredException для просроченного токена")
        void shouldThrowTokenExpiredExceptionWhenTokenIsExpired() {
            TokenEntity expiredToken = tokenFactory.create(principal, Duration.ofMillis(-10), TokenType.ACCESS_TOKEN);

            assertThatThrownBy(() -> tokenExtractor.extract("Bearer " + expiredToken.getToken()))
                    .isInstanceOf(TokenExpiredException.class);
        }

        @Test
        @DisplayName("Выбрасывает TokenParseException при неверном формате токена")
        void shouldThrowTokenParseExceptionForInvalidFormat() {
            assertThatThrownBy(() -> tokenExtractor.extract("InvalidTokenFormat"))
                    .isInstanceOf(TokenParseException.class);
        }

        @Test
        @DisplayName("Выбрасывает TokenVerificationException при недействительной подписи")
        void shouldThrowTokenVerificationExceptionForInvalidSignature() throws Exception {
            TokenEntity validToken = tokenFactory.create(principal, Duration.ofHours(1), TokenType.ACCESS_TOKEN);

            KeyPair otherKeyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
            JWSVerifier otherVerifier = new RSASSAVerifier((RSAPublicKey) otherKeyPair.getPublic());
            NimbusTokenExtractorAdapter maliciousExtractor = new NimbusTokenExtractorAdapter(otherVerifier);

            assertThatThrownBy(() -> maliciousExtractor.extract("Bearer " + validToken.getToken()))
                    .isInstanceOf(TokenVerificationException.class);
        }
    }
}