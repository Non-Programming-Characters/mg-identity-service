package ru.solomka.identity.spring.configuration.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.solomka.identity.principal.PrincipalRepository;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.principal.PrincipalSpringSecurityAdapter;

@Configuration
public class PrincipalConfiguration {

    @Bean
    PrincipalService principalService(PrincipalRepository principalRepository) {
        return new PrincipalService(principalRepository);
    }

    @Bean
    PrincipalSpringSecurityAdapter principalSpringSecurityAdapter() {
        return new PrincipalSpringSecurityAdapter();
    }
}