package ru.solomka.identity.kafka.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.kafka.entity.KafkaUserEntity;
import ru.solomka.identity.principal.PrincipalEntity;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Setter @Getter
public class KafkaUserDeletedEvent extends KafkaEvent<KafkaUserEntity, PrincipalEntity> {
    @NonNull KafkaUserEntity firstChapterNotifyMessage;
    @NonNull PrincipalEntity secondChapterNotifyMessage;
}