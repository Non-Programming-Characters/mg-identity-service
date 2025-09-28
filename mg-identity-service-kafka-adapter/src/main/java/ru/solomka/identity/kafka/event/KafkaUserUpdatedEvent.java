package ru.solomka.identity.kafka.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.user.UserEntity;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KafkaUserUpdatedEvent extends KafkaEvent<UserEntity, PrincipalEntity> {
    @NonNull UserEntity firstChapterNotifyMessage;
    @NonNull PrincipalEntity secondChapterNotifyMessage;
}