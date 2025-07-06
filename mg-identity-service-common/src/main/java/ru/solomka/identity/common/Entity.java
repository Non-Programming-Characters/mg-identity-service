package ru.solomka.identity.common;

import java.time.Instant;
import java.util.UUID;

public interface Entity {

    UUID getId();
    void setId(UUID id);

    Instant getCreatedAt();
    void setCreatedAt(Instant instant);
}