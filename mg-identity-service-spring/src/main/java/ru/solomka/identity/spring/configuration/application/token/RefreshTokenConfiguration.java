package ru.solomka.identity.spring.configuration.application.token;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.solomka.identity.common.mapper.Mapper;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.spring.configuration.properties.TokenPropertiesConfiguration;
import ru.solomka.identity.token.*;
import ru.solomka.identity.token.cqrs.handler.IssueTokenPairCommandHandler;
import ru.solomka.identity.user.UserService;

@Configuration
@EntityScan(basePackageClasses = JpaRefreshTokenEntity.class)
@EnableJpaRepositories(basePackageClasses = JpaRefreshTokenEntityRepository.class)
public class RefreshTokenConfiguration {

    @Bean
    IssueTokenPairCommandHandler issueTokenPairCommandHandler(RefreshTokenService refreshTokenService,
                                                              TokenExtractor tokenExtractor,
                                                              UserService userService,
                                                              PrincipalService principalService,
                                                              TokenPairFactory tokenPairFactory,
                                                              TokenPropertiesConfiguration tokenPropertiesConfiguration) {
        return new IssueTokenPairCommandHandler(
                refreshTokenService,
                tokenExtractor,
                userService,
                principalService,
                tokenPairFactory,
                tokenPropertiesConfiguration.getAccessToken().getLifetime(),
                tokenPropertiesConfiguration.getRefreshToken().getLifetime()
        );
    }

    @Bean
    RefreshTokenService refreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        return new RefreshTokenService(refreshTokenRepository);
    }

    @Bean
    RefreshTokenRepository refreshTokenRepository(JpaRefreshTokenEntityRepository jpaRefreshTokenEntityRepository,
                                                  Mapper<JpaRefreshTokenEntity, RefreshTokenEntity> jpaRefreshTokenEntityRefreshTokenEntityMapper) {
        return new JpaRefreshTokenRepositoryAdapter(jpaRefreshTokenEntityRepository, jpaRefreshTokenEntityRefreshTokenEntityMapper);
    }

    @Bean
    JpaRefreshTokenEntityRefreshTokenEntityMapper jpaRefreshTokenEntityRefreshTokenEntityMapper() {
        return new JpaRefreshTokenEntityRefreshTokenEntityMapper();
    }
}