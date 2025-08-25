package ru.solomka.identity.spring.configuration.application;

import lombok.NonNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.solomka.identity.user.*;
import ru.solomka.identity.user.cqrs.command.ValidateUserCredentialCommandHandler;
import ru.solomka.identity.user.cqrs.query.GetUserByIdQueryHandler;

@Configuration
@EnableJpaRepositories(basePackageClasses = JpaUserRepository.class)
@EntityScan(basePackageClasses = JpaUserEntity.class)
public class UserConfiguration {

    @Bean
    UserService userService(@NonNull UserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    JpaUserEntityRepositoryAdapter userRepository(@NonNull JpaUserRepository jpaUserRepository,
                                                  @NonNull UserEntityJpaUserEntityMapper userEntityJpaUserEntityMapper) {
        return new JpaUserEntityRepositoryAdapter(jpaUserRepository, userEntityJpaUserEntityMapper);
    }

    @Bean
    UserEntityJpaUserEntityMapper userEntityMapper() {
        return new UserEntityJpaUserEntityMapper();
    }

    @Bean
    UserSearchResponseUserEntityMapper userEntityResponseUserEntityMapper() {
        return new UserSearchResponseUserEntityMapper();
    }

    @Bean
    GetUserByIdQueryHandler getUserByIdQueryHandler(@NonNull UserService userService) {
        return new GetUserByIdQueryHandler(userService);
    }
    @Bean
    ValidateUserCredentialCommandHandler validateUserCredentialCommandHandler(@NonNull UserService userService) {
        return new ValidateUserCredentialCommandHandler(userService);
    }
}