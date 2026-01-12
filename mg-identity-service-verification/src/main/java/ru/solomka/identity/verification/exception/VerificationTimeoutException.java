package ru.solomka.identity.verification.exception;

public class VerificationTimeoutException extends RuntimeException {
    public VerificationTimeoutException(String message) {
        super(message);
    }
}
