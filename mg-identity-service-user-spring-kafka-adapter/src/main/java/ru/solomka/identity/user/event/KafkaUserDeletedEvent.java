package ru.solomka.identity.user.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Setter @Getter
public class KafkaUserDeletedEvent {

    @NonNull UUID id;

    @NonNull String login;

    @NonNull String email;

    @NonNull Instant createdAt;
}