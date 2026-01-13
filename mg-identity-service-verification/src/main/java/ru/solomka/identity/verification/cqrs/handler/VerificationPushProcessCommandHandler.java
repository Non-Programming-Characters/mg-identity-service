package ru.solomka.identity.verification.cqrs.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.user.UserService;
import ru.solomka.identity.verification.VerificationEntity;
import ru.solomka.identity.verification.VerificationService;
import ru.solomka.identity.verification.VerificationType;
import ru.solomka.identity.verification.cqrs.VerificationPushProcessCommand;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerificationPushProcessCommandHandler implements CommandHandler<VerificationPushProcessCommand, Boolean> {

    @NonNull VerificationService verificationService;

    @NonNull UserService userService;

    @NonNull Duration verificationLifetime;

    @Override
    public Boolean handle(VerificationPushProcessCommand command) {
        if(!userService.existsById(command.getEntityId()))
            throw new EntityNotFoundException("User with id '%s' not found".formatted(command.getEntityId()));

        if(verificationService.existsByReceiverId(command.getEntityId()) &&
                verificationService.getVerificationByReceiverId(command.getEntityId()).getExpiredAt().isAfter(Instant.now())) {

            verificationService.deleteById(verificationService.getVerificationByReceiverId(command.getEntityId()).getId());
        }

        VerificationEntity verificationEntity = VerificationEntity.builder()
                .receiverId(command.getEntityId())
                .payload(UUID.randomUUID().toString().substring(0, 5))
                .type(VerificationType.ACCOUNT_ACTIVATION)
                .expiredAt(Instant.now().plus(verificationLifetime.toSeconds(), ChronoUnit.SECONDS))
                .build();

        verificationService.create(verificationEntity);

        return true;
    }
}