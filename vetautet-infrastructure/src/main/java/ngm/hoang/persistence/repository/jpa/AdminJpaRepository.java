package ngm.hoang.persistence.repository.jpa;

import ngm.hoang.persistence.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminJpaRepository extends JpaRepository<AdminEntity, UUID> {
}
