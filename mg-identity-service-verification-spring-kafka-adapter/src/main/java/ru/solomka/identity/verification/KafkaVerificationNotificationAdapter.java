package ru.solomka.identity.verification;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import ru.solomka.identity.common.EntityNotification;
import ru.solomka.identity.verification.event.KafkaNotificationEvent;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaVerificationNotificationAdapter implements EntityNotification<VerificationEntity> {

    @NonNull KafkaTemplate<String, KafkaNotificationEvent> sendNotification;

    @Override
    public void notifyCreate(VerificationEntity message) {
        sendNotification.send(
                KafkaVerificationNotificationTopicPoints.SEND_VERIFICATION_NOTIFICATION_EVENT_TOPIC,
                new KafkaNotificationEvent(
                        message.getReceiverId(), "Активация аккаунта",
                        "Код активации аккаунта: %s. Никому не сообщайте данный код!",
                        message.getPayload()
                )
        );
    }

    @Override
    public void notifyUpdate(VerificationEntity message) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void notifyDelete(VerificationEntity message) {
        throw new UnsupportedOperationException();
    }
}