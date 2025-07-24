package ru.solomka.identity.authentication.cqrs.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.authentication.EncoderDelegate;
import ru.solomka.identity.authentication.cqrs.RegistrationCommand;
import ru.solomka.identity.authentication.exception.CredentialsCollisionException;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserService;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationCommandHandler implements CommandHandler<RegistrationCommand, UserEntity> {

    @NonNull PrincipalService principalService;
    @NonNull UserService userService;

    @NonNull EncoderDelegate encoderDelegate;

    @Override
    public UserEntity handle(RegistrationCommand command) {

        if(command.getLogin().isEmpty() || command.getPassword().isEmpty() || command.getEmail().isEmpty()
                || command.getFirstName().isEmpty() || command.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        if(userService.findByLogin(command.getLogin()).isPresent())
            throw new CredentialsCollisionException("User with login '%s' already exists".formatted(command.getLogin()));

        if(userService.findByEmail(command.getEmail()).isPresent())
            throw new CredentialsCollisionException("User with email '%s' already exists".formatted(command.getEmail()));

        return userService.create(UserEntity.builder()
                .login(command.getLogin())
                .passwordHash(encoderDelegate.encode(command.getPassword()))
                .email(command.getEmail())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .build());
    }
}