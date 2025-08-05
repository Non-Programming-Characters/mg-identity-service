package ru.solomka.identity.test.token;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class NimbusTokenFactoryExtractorTest {

    @Mock
    private NimbusTokenFactoryAdapter tokenFactory;

    @Mock
    private NimbusTokenExtractorAdapter tokenExtractor;

    private PrincipalEntity principal;

    private final UUID uuid = UUID.randomUUID();
    private final String username = "testuser";

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

        principal = new PrincipalEntity(uuid, username);
    }

    @Test
    void createAndValidateToken() throws TokenException {
        TokenEntity token = tokenFactory.create(principal, Duration.ofHours(1), TokenType.ACCESS_TOKEN);

        assertNotNull(token);
        assertEquals(principal.getId(), token.getUserId());
        assertTrue(token.getExpiredAt().isAfter(java.time.Instant.now()));

        TokenEntity extracted = tokenExtractor.extract("Bearer " + token.getToken());

        assertEquals(token.getId(), extracted.getId());
        assertEquals(token.getUserId(), extracted.getUserId());
        assertEquals(token.getTokenType(), extracted.getTokenType());
    }

    @Test
    void shouldThrowWhenTokenExpired() {
        TokenEntity token = tokenFactory.create(principal, Duration.ofMillis(-10), TokenType.ACCESS_TOKEN);

        assertThrows(TokenExpiredException.class,
                () -> tokenExtractor.extract("Bearer " + token.getToken()));
    }

    @Test
    void shouldThrowWhenInvalidTokenFormat() {
        assertThrows(TokenParseException.class,
                () -> tokenExtractor.extract("InvalidTokenFormat"));
    }

    @Test
    void shouldThrowWhenSignatureInvalid() throws Exception {
        KeyPair otherKeyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        JWSVerifier otherVerifier = new RSASSAVerifier((RSAPublicKey) otherKeyPair.getPublic());

        NimbusTokenExtractorAdapter extractorWithOtherVerifier =
                new NimbusTokenExtractorAdapter(otherVerifier);

        TokenEntity token = tokenFactory.create(principal, Duration.ofHours(1), TokenType.ACCESS_TOKEN);

        assertThrows(TokenVerificationException.class,
                () -> extractorWithOtherVerifier.extract("Bearer " + token.getToken()));
    }
}