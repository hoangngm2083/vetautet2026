package ngm.hoang.persistence.repository.adapter;

import lombok.RequiredArgsConstructor;
import ngm.hoang.model.Booking;
import ngm.hoang.persistence.repository.jpa.BookingJpaRepository;
import ngm.hoang.repository.BookingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BookingRepositoryImpl implements BookingRepository {
    private final BookingJpaRepository jpaMapper;

    @Override
    public Booking save(Booking booking) {
        return null;
    }

    @Override
    public Optional<Booking> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public boolean existsByTripIdAndSeatId(UUID tripId, UUID seatId) {
        return false;
    }
}
