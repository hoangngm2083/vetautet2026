package ngm.hoang.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ngm.hoang.model.enums.TripStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
@Getter
@Setter
@SuperBuilder
public class TripEntity extends BaseEntity {

    @Column(nullable = false)
    private String trainName;

    @Column(nullable = false)
    private String departureStation;

    @Column(nullable = false)
    private String arrivalStation;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatus status;
}
