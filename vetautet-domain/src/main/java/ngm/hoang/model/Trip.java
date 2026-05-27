package ngm.hoang.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ngm.hoang.model.enums.TripStatus;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class Trip extends BaseModel {

    private String trainName;

    private String departureStation;

    private String arrivalStation;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private TripStatus status;
}
