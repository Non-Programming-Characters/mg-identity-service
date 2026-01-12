package ru.solomka.identity.user.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.user.entity.KafkaUserEntity;

import java.time.Instant;
import java.util.UUID;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Setter @Getter
public class KafkaUserCreatedEvent {

    @NonNull UUID id;

    @NonNull String login;

    @NonNull String email;

    @NonNull Instant createdAt;
}