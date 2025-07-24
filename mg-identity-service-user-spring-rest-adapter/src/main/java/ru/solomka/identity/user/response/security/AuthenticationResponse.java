package ru.solomka.identity.user.response.security;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class AuthenticationResponse {

    @NonNull String accessToken;
    @NonNull String refreshToken;
}