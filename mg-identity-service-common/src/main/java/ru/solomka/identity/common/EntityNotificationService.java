package ru.solomka.identity.common;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.principal.PrincipalService;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EntityNotificationService<E extends Entity> {

    @NonNull EntityNotification<E, PrincipalEntity> entityNotification;
    @NonNull PrincipalService principalService;

    public void notifyCreated(E entity) {
        entityNotification.notifyCreate(entity, principalService.getPrincipal());
    }
    public void notifyDeleted(E entity) {
        entityNotification.notifyDelete(entity, principalService.getPrincipal());
    }
    public void notifyUpdated(E entity) {
        entityNotification.notifyUpdate(entity, principalService.getPrincipal());
    }
}
