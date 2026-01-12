package ru.solomka.identity.verification;

import lombok.*;
import ru.solomka.identity.common.Entity;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class VerificationEntity implements Entity {

    UUID id;

    @NonNull UUID receiverId;

    @NonNull String payload;

    @NonNull VerificationType type;

    @NonNull Instant expiredAt;

    Instant createdAt;
}