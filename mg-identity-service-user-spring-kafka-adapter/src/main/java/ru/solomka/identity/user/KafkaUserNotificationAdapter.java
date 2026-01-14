package ru.solomka.identity.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import ru.solomka.identity.common.EntityNotification;
import ru.solomka.identity.user.event.KafkaUserCreatedEvent;
import ru.solomka.identity.user.event.KafkaUserDeletedEvent;
import ru.solomka.identity.user.event.KafkaUserUpdatedEvent;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KafkaUserNotificationAdapter implements EntityNotification<UserEntity> {

    @NonNull KafkaTemplate<String, KafkaUserCreatedEvent> createNotification;
    @NonNull KafkaTemplate<String, KafkaUserUpdatedEvent> updateNotification;
    @NonNull KafkaTemplate<String, KafkaUserDeletedEvent> deleteNotification;

    @Override
    public void notifyCreate(UserEntity user) {
        createNotification.send(KafkaUserNotificationTopicPoints.CREATE_USER_EVENT_TOPIC, new KafkaUserCreatedEvent(user.getId(), user.getLogin(), user.getEmail(), user.getCreatedAt()));
    }

    @Override
    public void notifyUpdate(UserEntity user) {
        updateNotification.send(KafkaUserNotificationTopicPoints.UPDATE_USER_EVENT_TOPIC, new KafkaUserUpdatedEvent(user.getId(), user.getLogin(), user.getEmail(), user.getCreatedAt()));
    }

    @Override
    public void notifyDelete(UserEntity user) {
        deleteNotification.send(KafkaUserNotificationTopicPoints.DELETE_USER_EVENT_TOPIC, new KafkaUserDeletedEvent(user.getId(), user.getLogin(), user.getEmail(), user.getCreatedAt()));
    }
}