package ru.solomka.identity.common;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EntityNotificationService<E extends Entity> {

    @NonNull EntityNotification<E> entityNotification;

    public void notifyCreated(E entity) {
        entityNotification.notifyCreate(entity);
    }
    public void notifyDeleted(E entity) {
        entityNotification.notifyDelete(entity);
    }
    public void notifyUpdated(E entity) {
        entityNotification.notifyUpdate(entity);
    }
}
