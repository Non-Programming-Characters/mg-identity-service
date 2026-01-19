package ru.solomka.identity.spring.configuration.application;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.solomka.identity.authentication.AuthenticationService;
import ru.solomka.identity.authentication.EncoderDelegate;
import ru.solomka.identity.authentication.EncoderDelegateAdapter;
import ru.solomka.identity.authentication.cqrs.handler.AuthenticationCommandHandler;
import ru.solomka.identity.authentication.cqrs.handler.RegistrationCommandHandler;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.spring.configuration.properties.TokenPropertiesConfiguration;
import ru.solomka.identity.token.TokenPairFactory;
import ru.solomka.identity.user.UserService;

@Configuration
public class AuthenticationConfiguration {

    @Bean
    AuthenticationService authenticationService(@NonNull PrincipalService principalService,
                                                @NonNull UserService userService,
                                                @NonNull EncoderDelegate encoderDelegate) {
        return new AuthenticationService(principalService, userService, encoderDelegate);
    }

    @Bean
    EncoderDelegateAdapter encoderDelegate(@NonNull PasswordEncoder passwordEncoder) {
        return new EncoderDelegateAdapter(passwordEncoder);
    }

    @Bean
    AuthenticationCommandHandler authenticationCommandHandler(@NonNull AuthenticationService authenticationService,
                                                              @NonNull TokenPairFactory tokenPairFactory,
                                                              @NonNull TokenPropertiesConfiguration tokenPropertiesConfiguration) {
        return new AuthenticationCommandHandler(
                authenticationService,
                tokenPairFactory,
                tokenPropertiesConfiguration.getAccessToken().getLifetime(),
                tokenPropertiesConfiguration.getRefreshToken().getLifetime()
        );
    }
    @Bean
    RegistrationCommandHandler registrationCommandHandler(@NonNull EncoderDelegate encoderDelegate,
                                                          @NonNull UserService userService
    ) {
        return new RegistrationCommandHandler(
                userService,
                encoderDelegate
        );
    }
}