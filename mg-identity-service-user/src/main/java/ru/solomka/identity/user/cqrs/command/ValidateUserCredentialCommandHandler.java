package ru.solomka.identity.user.cqrs.command;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserService;
import ru.solomka.identity.user.exception.CredentialValidationException;
import ru.solomka.identity.user.exception.ValidationException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ValidateUserCredentialCommandHandler implements CommandHandler<ValidateUserCredentialCommand, String> {

    @NonNull UserService userService;

    @Override
    public String handle(ValidateUserCredentialCommand command) {

        List<String> variableFieldsForValidate = Arrays.stream(UserEntity.class.getDeclaredFields())
                .map(Field::getName)
                .filter(field -> !Pattern.compile(".*[A-Z].*").matcher(field).matches() && (!field.equals("id") && !field.equals("password")))
                .toList();

        if(command.getType().isEmpty())
            throw new IllegalArgumentException("Type validation cannot be empty");

        if(command.getData().isEmpty())
            throw new IllegalArgumentException("Payload (Data) for validation cannot be empty");

        if(!variableFieldsForValidate.contains(command.getType().toLowerCase()))
            throw new ValidationException("The specified type of validation is not supported");

        switch (command.getType()) {
            case "login" -> {
                Pattern userLoginPattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9-\\\\.]{1,20}$");

                Optional<UserEntity> optionalUserEntity = userService.findByLogin(command.getData());

                if(optionalUserEntity.isPresent())
                    throw new CredentialValidationException("The login validation attempt failed (Login already exists)");

                if(!userLoginPattern.matcher(command.getData()).matches())
                    throw new CredentialValidationException("The login validation attempt failed (Incorrect login content format)");

                return command.getData();
            }

            case "email" -> {
                Pattern emailPattern = Pattern.compile(
                        "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
                        Pattern.CASE_INSENSITIVE
                );

                if(!emailPattern.matcher(command.getData()).matches())
                    throw new CredentialValidationException("The email validation attempt failed (Invalid email content format)");

                return command.getData();
            }

            default -> throw new ValidationException("The type of validation is not supported");
        }
    }
}
