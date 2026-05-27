package ngm.hoang.websocket.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatEventMessage {

    private String seatId;

    private SeatEventType type;

    private String sessionId;
}
