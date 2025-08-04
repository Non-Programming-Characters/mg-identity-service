package ru.solomka.identity.test;

import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.token.*;

import java.time.Duration;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TokenTest {

    @Mock
    JWSSigner jwsSigner;

    @Mock
    JWSHeader jwsHeader;

    @Mock
    JWSVerifier jwsVerifier;

    NimbusTokenFactoryAdapter tokenFactoryAdapter;
    NimbusTokenExtractorAdapter tokenExtractorAdapter;

    private final UUID userUID =  UUID.randomUUID();
    private final String username = "testuser";

    @BeforeEach
    public void setUp() {
        tokenFactoryAdapter = new NimbusTokenFactoryAdapter(jwsSigner, jwsHeader);
        tokenExtractorAdapter = new NimbusTokenExtractorAdapter(jwsVerifier);
    }

    @Test
    public void testCreateAccessTokenFactory() {
        PrincipalEntity principalEntity = PrincipalEntity.builder().id(userUID).username(username).build();
        Mockito.when(tokenFactoryAdapter.create(principalEntity, Duration.ofDays(1), TokenType.ACCESS_TOKEN)).thenReturn(Mockito.any(TokenEntity.class));
    }

    @Test
    public void validateAccessToken() {
        PrincipalEntity principalEntity = PrincipalEntity.builder().id(userUID).username(username).build();
        TokenEntity accessToken = tokenFactoryAdapter.create(principalEntity, Duration.ofDays(1), TokenType.ACCESS_TOKEN);
        Mockito.when(tokenExtractorAdapter.extract(accessToken.getToken())).thenReturn(accessToken);
    }
}