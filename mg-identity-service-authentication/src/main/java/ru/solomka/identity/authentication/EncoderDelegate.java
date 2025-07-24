package ru.solomka.identity.authentication;

public interface EncoderDelegate {
    String encode(String value);
    boolean matches(String encodedValue, String decodedValue);
}