package ru.solomka.identity.spring.configuration.properties;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix="service")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerificationPropertiesConfiguration {

    @NonNull VerificationProperties verificationProperties;

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class VerificationProperties {
        Duration lifetime;
    }
}