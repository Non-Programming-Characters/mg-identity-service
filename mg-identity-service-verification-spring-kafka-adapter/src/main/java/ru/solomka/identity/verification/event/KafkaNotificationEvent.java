package ru.solomka.identity.verification.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Setter @Getter
@ToString
public class KafkaNotificationEvent {

    @NonNull UUID receiverId;

    @NonNull String subject;

    @NonNull String message;

    @NonNull String payload;
}