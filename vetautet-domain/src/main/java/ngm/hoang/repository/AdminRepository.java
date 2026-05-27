package ngm.hoang.repository;

import ngm.hoang.model.Admin;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository {
    Admin save(Admin admin);

    Optional<Admin> findById(UUID id);
}
