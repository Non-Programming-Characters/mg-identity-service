package ru.solomka.identity.common;

public interface EntityNotification<M> {
    void notifyCreate(M message);
    void notifyUpdate(M message);
    void notifyDelete(M message);
}