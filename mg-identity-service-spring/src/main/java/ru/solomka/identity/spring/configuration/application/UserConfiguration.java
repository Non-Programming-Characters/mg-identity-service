package ru.solomka.identity.spring.configuration.application;

import lombok.NonNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.solomka.identity.user.*;

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
    UserEntityResponseUserEntityMapper userEntityResponseUserEntityMapper() {
        return new UserEntityResponseUserEntityMapper();
    }
}