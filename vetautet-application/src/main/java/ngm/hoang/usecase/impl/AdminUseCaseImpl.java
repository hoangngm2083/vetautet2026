package ngm.hoang.usecase.impl;

import lombok.RequiredArgsConstructor;
import ngm.hoang.model.Admin;
import ngm.hoang.provider.Hasher;
import ngm.hoang.repository.AdminRepository;
import ngm.hoang.service.AdminService;
import ngm.hoang.usecase.AdminUseCase;
import ngm.hoang.usecase.command.CreateAdminCommand;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUseCaseImpl implements AdminUseCase {
    private final AdminService adminService;
    private final AdminRepository adminRepository;
    private final Hasher hasher;

    @Override
    public Admin create(CreateAdminCommand command) {
        String passwordHash = hasher.hash(command.password());
        Admin model = adminService.create(command.username(), passwordHash);
        return adminRepository.save(model);
    }
}
