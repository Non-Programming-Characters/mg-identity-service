package ru.solomka.identity.authentication.exception;

public class CredentialsCollisionException extends RuntimeException {
    public CredentialsCollisionException(String message) {
        super(message);
    }
}
