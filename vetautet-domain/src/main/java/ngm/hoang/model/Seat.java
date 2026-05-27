package ngm.hoang.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ngm.hoang.model.enums.SeatClass;

@Getter
@SuperBuilder
public class Seat extends BaseModel {

    private Trip trip;

    private String seatNumber;

    private SeatClass seatClass;
}
