package ngm.hoang.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        name = "bookings",
        indexes = {
                @Index(name = "idx_booking_trip", columnList = "trip_id"),
                @Index(name = "idx_booking_email", columnList = "customer_email")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_trip_seat_booking",
                        columnNames = {"trip_id", "seat_id"}
                )
        }
)
@Getter
@Setter
@SuperBuilder
public class BookingEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private TripEntity trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private SeatEntity seat;

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false, unique = true)
    private String bookingCode;
}
