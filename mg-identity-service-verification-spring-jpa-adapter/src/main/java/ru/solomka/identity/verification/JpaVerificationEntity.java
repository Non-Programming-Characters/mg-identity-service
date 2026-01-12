package ru.solomka.identity.verification;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "verifications")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class JpaVerificationEntity {

    @Id
    @Column(name = "id", nullable = false)
    @NonNull UUID id;

    @Column(name = "receiver_id", nullable = false)
    @NonNull UUID receiverId;

    @Column(name = "payload", nullable = false)
    @NonNull String payload;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @NonNull VerificationType type;

    @Column(name = "expired_at", nullable = false)
    @NonNull Instant expiredAt;

    @Column(name = "created_at", nullable = false)
    @NonNull Instant createdAt;
}