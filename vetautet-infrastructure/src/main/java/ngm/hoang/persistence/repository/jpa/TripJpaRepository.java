package ngm.hoang.persistence.repository.jpa;

import ngm.hoang.persistence.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TripJpaRepository extends JpaRepository<TripEntity, UUID> {
}
