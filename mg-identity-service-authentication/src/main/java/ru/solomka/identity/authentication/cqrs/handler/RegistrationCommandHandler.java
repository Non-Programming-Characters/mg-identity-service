package ru.solomka.identity.authentication.cqrs.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.authentication.EncoderDelegate;
import ru.solomka.identity.authentication.cqrs.RegistrationCommand;
import ru.solomka.identity.authentication.exception.CredentialsCollisionException;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserService;

import java.util.UUID;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationCommandHandler implements CommandHandler<RegistrationCommand, UserEntity> {

    @NonNull UserService userService;
    @NonNull EncoderDelegate encoderDelegate;
    @NonNull PrincipalService principalService;

    @Override
    public UserEntity handle(RegistrationCommand command) {

        if(command.getLogin().isEmpty() || command.getPassword().isEmpty() || command.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        try {
            if( userService.getByLogin(command.getLogin()) != null)
                throw new CredentialsCollisionException("User with login '%s' already exists".formatted(command.getLogin()));

            if(userService.getByEmail(command.getEmail()) != null)
                throw new CredentialsCollisionException("User with email '%s' already exists".formatted(command.getEmail()));
        } catch (EntityNotFoundException e) {
            UserEntity userEntity = UserEntity.builder()
                    .id(UUID.randomUUID())
                    .login(command.getLogin())
                    .email(command.getEmail())
                    .passwordHash(encoderDelegate.encode(command.getPassword()))
                    .build();

            principalService.setPrincipal(PrincipalEntity.builder().id(userEntity.getId()).username(userEntity.getLogin()).build());

            return userService.create(userEntity);
        }
        return null;
    }
}