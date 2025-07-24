package ru.solomka.identity.authentication;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EncoderDelegateAdapter implements EncoderDelegate {

    @NonNull PasswordEncoder passwordEncoder;

    @Override
    public String encode(String value) {
        return passwordEncoder.encode(value);
    }

    @Override
    public boolean matches(String encodedValue, String decodedValue) {
        return passwordEncoder.matches(decodedValue, encodedValue);
    }
}
