package ru.solomka.identity.common.cqrs;

public interface CommandHandler<A, R> {
    R handle(A command);
}