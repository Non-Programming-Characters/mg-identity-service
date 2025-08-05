package ru.solomka.identity.spring.configuration.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import ru.solomka.identity.authentication.exception.CredentialsCollisionException;
import ru.solomka.identity.common.exception.EntityAlreadyExistsException;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.principal.exception.PrincipalException;
import ru.solomka.identity.spring.configuration.exception.provider.DefaultExceptionFormatProvider;
import ru.solomka.identity.spring.configuration.exception.provider.ErrorResponseFormatProvider;
import ru.solomka.identity.spring.configuration.exception.provider.ProviderExceptionFormat;
import ru.solomka.identity.token.exception.TokenException;
import ru.solomka.identity.token.exception.TokenExpiredException;
import ru.solomka.identity.token.exception.TokenParseException;
import ru.solomka.identity.token.exception.TokenVerificationException;

import java.util.List;

@Configuration
public class ExceptionAdviceConfiguration {

    @Bean
    @Order(2)
    ExceptionFormatProvider defaultExceptionFormatProvider() {
        return new DefaultExceptionFormatProvider();
    }

    @Bean
    @Order(1)
    ExceptionFormatProvider errorResponseExceptionFormatProvider() {
        return new ErrorResponseFormatProvider();
    }

    @Bean
    @Order(0)
    ExceptionFormatProvider entityNotFoundExceptionFormatProvider() {
        return new StatusCodeRangeExceptionFormatProvider(
                404,
                EntityNotFoundException.class
        );
    }

    @Bean
    @Order(0)
    ExceptionFormatProvider principalExceptionFormatProvider() {
        return new StatusCodeRangeExceptionFormatProvider(
                400,
                PrincipalException.class
        );
    }

    @Bean
    @Order(0)
    ExceptionFormatProvider credentialCollistionExceptionFormatProvider() {
        return new StatusCodeRangeExceptionFormatProvider(
                409,
                CredentialsCollisionException.class
        );
    }

    @Bean
    @Order(0)
    ExceptionFormatProvider tokenExceptionFormatProvider() {
        return new StatusCodeRangeExceptionFormatProvider(
                400,
                TokenException.class
        );
    }
    @Bean
    @Order(0)
    ExceptionFormatProvider tokenExpiredExceptionFormatProvider() {
        return new StatusCodeRangeExceptionFormatProvider(
                422,
                TokenExpiredException.class
        );
    }

    @Bean
    @Order(0)
    ExceptionFormatProvider tokenParseExceptionFormatProvider() {
        return new StatusCodeRangeExceptionFormatProvider(
                422,
                TokenParseException.class
        );
    }

    @Bean
    @Order(0)
    ExceptionFormatProvider tokenVerificationExceptionFormatProvider() {
        return new StatusCodeRangeExceptionFormatProvider(
                422,
                TokenVerificationException.class
        );
    }

    @Bean
    @Order(0)
    ExceptionFormatProvider enityAlreadyExistsExceptionFormatProvider() {
        return new StatusCodeRangeExceptionFormatProvider(
                409,
                EntityAlreadyExistsException.class
        );
    }

    @Bean
    ExceptionFormatFactory exceptionFormatFactory(
            @NotNull List<ExceptionFormatProvider> exceptionFormatProviders
    ) {
        return new ProviderExceptionFormat(exceptionFormatProviders);
    }
}