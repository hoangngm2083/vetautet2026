package ngm.hoang.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@SuperBuilder
public class Booking extends BaseModel {

    private Trip trip;

    private Seat seat;

    private String customerEmail;

    private String bookingCode;

    private Instant bookedAt;

}
