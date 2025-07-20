package ru.solomka.identity.token.exception;

public class TokenParseException extends RuntimeException {
    public TokenParseException(String message) {
        super(message);
    }
}
