package ru.solomka.identity.kafka;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import ru.solomka.identity.common.EntityNotification;
import ru.solomka.identity.kafka.event.KafkaUserCreatedEvent;
import ru.solomka.identity.kafka.event.KafkaUserDeletedEvent;
import ru.solomka.identity.kafka.event.KafkaUserUpdatedEvent;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.user.UserEntity;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KafkaNotificationAdapter implements EntityNotification<UserEntity, PrincipalEntity> {

    @NonNull KafkaTemplate<String, KafkaUserCreatedEvent> createNotification;
    @NonNull KafkaTemplate<String, KafkaUserUpdatedEvent> updateNotification;
    @NonNull KafkaTemplate<String, KafkaUserDeletedEvent> deleteNotification;

    @Override
    public void notifyCreate(UserEntity user, PrincipalEntity principal) {
        createNotification.send(KafkaTopicPoints.CREATE_EVENT_TOPIC, new KafkaUserCreatedEvent(user, principal));
    }

    @Override
    public void notifyUpdate(UserEntity user, PrincipalEntity principal) {
        updateNotification.send(KafkaTopicPoints.UPDATE_EVENT_TOPIC, new KafkaUserUpdatedEvent(user, principal));
    }

    @Override
    public void notifyDelete(UserEntity user, PrincipalEntity principal) {
        deleteNotification.send(KafkaTopicPoints.DELETE_EVENT_TOPIC, new KafkaUserDeletedEvent(user, principal));
    }
}
