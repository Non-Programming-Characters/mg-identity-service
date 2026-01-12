package ru.solomka.identity.verification.cqrs.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserService;
import ru.solomka.identity.user.UserStatus;
import ru.solomka.identity.verification.VerificationEntity;
import ru.solomka.identity.verification.VerificationPair;
import ru.solomka.identity.verification.VerificationService;
import ru.solomka.identity.verification.VerificationType;
import ru.solomka.identity.verification.cqrs.VerificationHandleProcessCommand;
import ru.solomka.identity.verification.exception.VerificationException;
import ru.solomka.identity.verification.exception.VerificationTimeoutException;

import java.time.Instant;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerificationHandleProcessCommandHandler implements CommandHandler<VerificationHandleProcessCommand, Boolean> {

    @NonNull VerificationService verificationService;

    @NonNull UserService userService;

    @Override
    public Boolean handle(VerificationHandleProcessCommand command) {
        if(command.getPayload().isEmpty())
            throw new VerificationException("Verification payload cannot be null or empty");

        if(!userService.existsById(command.getEntityId()))
            throw new EntityNotFoundException("Entity for verification with id '%s' not found".formatted(command.getEntityId()));

        if(!verificationService.existsByReceiverId(command.getEntityId()))
            throw new EntityNotFoundException("Verification receiver with id '%s' not found".formatted(command.getEntityId()));

        VerificationEntity verificationEntity = verificationService.getVerificationByReceiverId(command.getEntityId());

        if(verificationEntity.getExpiredAt().isAfter(Instant.now())) {
            verificationService.deleteById(verificationEntity.getId());
            throw new VerificationTimeoutException("The verification payload has expired");
        }

        if(!command.getPayload().equals(verificationEntity.getPayload()))
            throw new VerificationException("Invalid verification payload");

        if(verificationEntity.getType() == VerificationType.ACCOUNT_ACTIVATION) {
            UserEntity userEntity = userService.getById(command.getEntityId());
            userEntity.setStatus(UserStatus.VERIFIED);
            userService.update(userEntity);
        }

        verificationService.deleteById(verificationService.getVerificationByReceiverId(command.getEntityId()).getId());
        verificationService.clearAllExpireVerifications();

        return true;
    }
}