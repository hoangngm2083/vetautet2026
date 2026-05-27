package ngm.hoang.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ngm.hoang.model.enums.SeatHoldStatus;

import java.time.Instant;

@Getter
@SuperBuilder
public class SeatHold extends BaseModel {


    private Trip trip;

    private Seat seat;

    private String sessionId;

    private Instant expiredAt;

    private SeatHoldStatus status;

    public boolean isExpired() {
        return Instant.now().isAfter(expiredAt);
    }
}
