package ru.solomka.identity.spring.configuration.application;

import lombok.NonNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.solomka.identity.common.EntityNotification;
import ru.solomka.identity.common.EntityNotificationService;
import ru.solomka.identity.common.mapper.Mapper;
import ru.solomka.identity.spring.configuration.properties.VerificationPropertiesConfiguration;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserService;
import ru.solomka.identity.verification.*;
import ru.solomka.identity.verification.cqrs.handler.VerificationHandleProcessCommandHandler;
import ru.solomka.identity.verification.cqrs.handler.VerificationPushProcessCommandHandler;

@Configuration
@EnableJpaRepositories(basePackageClasses = CrudVerificationRepository.class)
@EntityScan(basePackageClasses = JpaVerificationEntity.class)
public class VerificationConfiguration {

    @Bean
    VerificationService verificationService(@NonNull VerificationRepository verificationRepository,
                                            EntityNotificationService<VerificationEntity> verificationEntityNotificationService) {
        return new VerificationService(verificationRepository, verificationEntityNotificationService);
    }

    @Bean
    CrudVerificationRepositoryAdapter crudVerificationRepositoryAdapter(@NonNull CrudVerificationRepository crudVerificationRepository,
                                                                        @NonNull Mapper<JpaVerificationEntity, VerificationEntity> jpaVerificationMapper) {
        return new CrudVerificationRepositoryAdapter(crudVerificationRepository, jpaVerificationMapper);
    }

    @Bean
    EntityNotificationService<VerificationEntity> verificationEntityEntityNotificationService(@NonNull EntityNotification<VerificationEntity> entityNotification) {
        return new EntityNotificationService<>(entityNotification);
    }

    @Bean
    JpaNotificationEntityNotificationEntityMapper jpaNotificationEntityNotificationEntityMapper() {
        return new JpaNotificationEntityNotificationEntityMapper();
    }

    @Bean
    VerificationPushProcessCommandHandler verificationPushProcessCommandHandler(@NonNull VerificationService verificationService,
                                                                                @NonNull UserService userService,
                                                                                @NonNull VerificationPropertiesConfiguration verificationPropertiesConfiguration) {
        return new VerificationPushProcessCommandHandler(
                verificationService,
                userService,
                verificationPropertiesConfiguration.getVerificationProperties().getLifetime()
        );
    }

    @Bean
    VerificationHandleProcessCommandHandler verificationHandleProcessCommandhandler(@NonNull VerificationService verificationService,
                                                                                    @NonNull EntityNotificationService<UserEntity> entityEntityNotificationService,
                                                                                    @NonNull UserService userService) {
        return new VerificationHandleProcessCommandHandler(verificationService, entityEntityNotificationService, userService);
    }
}