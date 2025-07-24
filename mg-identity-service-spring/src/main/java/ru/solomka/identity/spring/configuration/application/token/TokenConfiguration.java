package ru.solomka.identity.spring.configuration.application.token;

import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.solomka.identity.token.NimbusTokenExtractorAdapter;
import ru.solomka.identity.token.NimbusTokenFactoryAdapter;
import ru.solomka.identity.token.TokenFactory;
import ru.solomka.identity.token.TokenPairFactory;

@Configuration
public class TokenConfiguration {

    @Bean
    TokenPairFactory tokenPairFactory(TokenFactory refreshTokenFactory, TokenFactory accessTokenFactory) {
        return new TokenPairFactory(refreshTokenFactory, accessTokenFactory);
    }

    @Bean
    NimbusTokenExtractorAdapter nimbusTokenExtractorAdapter(JWSVerifier jwsVerifier) {
        return new NimbusTokenExtractorAdapter(jwsVerifier);
    }

    @Bean
    NimbusTokenFactoryAdapter nimbusTokenFactoryAdapter(JWSSigner jwsSigner, JWSHeader jwsHeader) {
        return new NimbusTokenFactoryAdapter(jwsSigner, jwsHeader);
    }
}