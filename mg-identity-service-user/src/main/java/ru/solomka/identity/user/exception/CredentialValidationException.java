package ru.solomka.identity.user.exception;

public class CredentialValidationException extends RuntimeException {
    public CredentialValidationException(String message) {
        super(message);
    }
}
