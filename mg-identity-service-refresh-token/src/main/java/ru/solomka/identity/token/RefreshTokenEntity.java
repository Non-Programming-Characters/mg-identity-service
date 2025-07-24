package ru.solomka.identity.token;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.Entity;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class RefreshTokenEntity implements Entity {

    UUID id;

    @NonNull Instant createdAt;
}