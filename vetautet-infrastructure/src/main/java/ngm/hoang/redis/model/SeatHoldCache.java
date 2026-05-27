package ngm.hoang.redis.model;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatHoldCache implements Serializable {

    private String sessionId;

    private Instant expiredAt;
}
