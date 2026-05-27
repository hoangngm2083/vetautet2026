package ngm.hoang.persistence.repository.jpa;

import ngm.hoang.persistence.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingJpaRepository extends JpaRepository<BookingEntity, UUID> {
}
