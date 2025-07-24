package ru.solomka.identity.user.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class UserSearchResponse {

    @NonNull UUID id;
    @NonNull String login;
    @NonNull String email;
    @NonNull String firstName;
    @NonNull String lastName;
    @NonNull Instant createdAt;
}
