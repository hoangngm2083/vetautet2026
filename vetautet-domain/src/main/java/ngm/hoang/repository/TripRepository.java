package ngm.hoang.repository;


import ngm.hoang.model.Trip;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TripRepository {

    Trip save(Trip trip);

    Optional<Trip> findById(UUID id);

    List<Trip> findAllOpenTrips();
}