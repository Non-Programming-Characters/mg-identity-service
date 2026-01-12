package ru.solomka.identity.authentication;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.authentication.exception.CredentialsException;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserService;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    @NonNull PrincipalService principalService;
    @NonNull UserService userService;
    @NonNull EncoderDelegate encoderDelegate;

    public @NonNull PrincipalEntity authenticate(String login, String password) {
        try {
            UserEntity userEntity = userService.getUserByLogin(login);

            if (!encoderDelegate.matches(userEntity.getPasswordHash(), password))
                throw new CredentialsException("Wrong password!");

            return principalService.setPrincipal(PrincipalEntity.builder().id(userEntity.getId()).username(userEntity.getLogin()).build());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }
}