package ru.solomka.identity.spring.configuration.application;

import lombok.NonNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.solomka.identity.common.EntityNotification;
import ru.solomka.identity.common.EntityNotificationService;
import ru.solomka.identity.user.*;
import ru.solomka.identity.user.cqrs.command.handler.ValidateUserCredentialCommandHandler;
import ru.solomka.identity.user.cqrs.query.handler.GetUserByEmailQueryHandler;
import ru.solomka.identity.user.cqrs.query.handler.GetUserByIdQueryHandler;
import ru.solomka.identity.user.cqrs.query.handler.GetUserByLoginQueryHandler;

@Configuration
@EnableJpaRepositories(basePackageClasses = CrudUserRepository.class)
@EntityScan(basePackageClasses = JpaUserEntity.class)
public class UserConfiguration {

    @Bean
    UserService userService(@NonNull UserRepository userRepository,
                            @NonNull EntityNotificationService<UserEntity> notificationService) {
        return new UserService(userRepository, notificationService);
    }

    @Bean
    JpaUserEntityRepositoryAdapter userRepository(@NonNull CrudUserRepository crudUserRepository,
                                                  @NonNull UserEntityJpaUserEntityMapper userEntityJpaUserEntityMapper) {
        return new JpaUserEntityRepositoryAdapter(crudUserRepository, userEntityJpaUserEntityMapper);
    }

    @Bean
    EntityNotificationService<UserEntity> userEntityNotificationService(@NonNull EntityNotification<UserEntity> entityNotification) {
        return new EntityNotificationService<>(entityNotification);
    }

    @Bean
    UserEntityJpaUserEntityMapper userEntityMapper() {
        return new UserEntityJpaUserEntityMapper();
    }

    @Bean
    GetUserByIdQueryHandler getUserByIdQueryHandler(@NonNull UserService userService) {
        return new GetUserByIdQueryHandler(userService);
    }

    @Bean
    GetUserByLoginQueryHandler getUserByLoginQueryHandler(@NonNull UserService userService) {
        return new GetUserByLoginQueryHandler(userService);
    }

    @Bean
    GetUserByEmailQueryHandler getUserByEmailQueryHandler(@NonNull UserService userService) {
        return new GetUserByEmailQueryHandler(userService);
    }

    @Bean
    ValidateUserCredentialCommandHandler validateUserCredentialCommandHandler(@NonNull UserService userService) {
        return new ValidateUserCredentialCommandHandler(userService);
    }
}