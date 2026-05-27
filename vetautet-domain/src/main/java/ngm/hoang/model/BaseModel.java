package ngm.hoang.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Getter
@SuperBuilder
public abstract class BaseModel {
    private final Long version;
    private final Instant createdAt;
    private final Instant updatedAt;
    private UUID id;

    static public UUID genId() {
        return UUID.randomUUID();
    }
}
