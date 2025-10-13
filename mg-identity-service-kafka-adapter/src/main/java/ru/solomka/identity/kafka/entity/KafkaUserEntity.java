package ru.solomka.identity.kafka.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class KafkaUserEntity {

    @NonNull UUID id;

    @NonNull String email;

    @NonNull Instant createdAt;
}