package ru.solomka.identity.spring.configuration.external;

import com.nimbusds.jose.jwk.RSAKey;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;

@Configuration
public class KeyFactoryConfiguration {

    @SneakyThrows
    @Bean
    public KeyFactory keyFactory() {
        return KeyFactory.getInstance("RSA");
    }
}
