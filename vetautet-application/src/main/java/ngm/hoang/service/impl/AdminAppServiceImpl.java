package ngm.hoang.service.impl;

import lombok.RequiredArgsConstructor;
import ngm.hoang.cqrs.admin.command.CreateAdminCommand;
import ngm.hoang.cqrs.admin.command.handler.CreateAdminCommandHandler;
import ngm.hoang.cqrs.admin.query.GetAdminByIdQuery;
import ngm.hoang.cqrs.admin.query.handler.GetAdminByIdQueryHandler;
import ngm.hoang.dto.AdminDTO;
import ngm.hoang.service.AdminAppService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminAppServiceImpl implements AdminAppService {
    private final CreateAdminCommandHandler createAdminCommandHandler;
    private final GetAdminByIdQueryHandler getAdminByIdQueryHandler;

    @Override
    public UUID create(CreateAdminCommand command) {
        return createAdminCommandHandler.handle(command);
    }

    @Override
    public AdminDTO get(GetAdminByIdQuery query) {
        return getAdminByIdQueryHandler.handle(query);
    }
}
