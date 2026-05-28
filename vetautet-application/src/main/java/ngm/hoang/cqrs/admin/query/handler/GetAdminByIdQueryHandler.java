package ngm.hoang.cqrs.admin.query.handler;

import lombok.RequiredArgsConstructor;
import ngm.hoang.cqrs.admin.query.GetAdminByIdQuery;
import ngm.hoang.dto.AdminDTO;
import ngm.hoang.exception.NotFoundDomainException;
import ngm.hoang.mapper.AdminDtoMapper;
import ngm.hoang.model.Admin;
import ngm.hoang.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAdminByIdQueryHandler {
    private final AdminRepository adminRepository;
    private final AdminDtoMapper adminDtoMapper;

    public AdminDTO handle(GetAdminByIdQuery query) {
        Admin admin = adminRepository.findById(query.id())
                .orElseThrow(() -> new NotFoundDomainException(Admin.class, "id", query.id().toString()));
        return adminDtoMapper.toDto(admin);
    }
}
