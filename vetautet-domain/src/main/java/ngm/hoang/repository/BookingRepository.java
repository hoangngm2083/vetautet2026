package ngm.hoang.repository;


import ngm.hoang.model.Booking;

import java.util.Optional;
import java.util.UUID;

public interface BookingRepository {

    Booking save(Booking booking);

    Optional<Booking> findById(UUID id);

    boolean existsByTripIdAndSeatId(UUID tripId, UUID seatId);
}
