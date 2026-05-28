package ngm.hoang.persistence.repository.adapter;

import lombok.RequiredArgsConstructor;
import ngm.hoang.model.Admin;
import ngm.hoang.persistence.entity.AdminEntity;
import ngm.hoang.persistence.repository.jpa.AdminJpaRepository;
import ngm.hoang.repository.AdminRepository;
import ngm.hoang.utils.mapper.AdminMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepository {
    private final AdminMapper mapper;
    private final AdminJpaRepository jpaRepository;

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    public Admin save(Admin admin) {
        AdminEntity entity = mapper.toEntity(admin);
        return mapper.toModel(jpaRepository.save(entity));
    }

    @Override
    public Optional<Admin> findById(UUID id) {
        return Optional.empty();
    }
}
