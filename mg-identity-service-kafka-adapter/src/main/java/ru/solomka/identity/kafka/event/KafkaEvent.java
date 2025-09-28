package ru.solomka.identity.kafka.event;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public abstract class KafkaEvent<V1, V2> {
    public abstract V1 getFirstChapterNotifyMessage();
    public abstract V2 getSecondChapterNotifyMessage();
}