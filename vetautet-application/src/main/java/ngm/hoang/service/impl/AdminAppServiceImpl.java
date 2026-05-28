package ngm.hoang.service.impl;

import lombok.RequiredArgsConstructor;
import ngm.hoang.cqrs.admin.command.CreateAdminCommand;
import ngm.hoang.cqrs.admin.command.handler.CreateAdminCommandHandler;
import ngm.hoang.model.Admin;
import ngm.hoang.service.AdminAppService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AdminAppServiceImpl implements AdminAppService {
    private final CreateAdminCommandHandler createAdminCommandHandler;

    @Override
    public Admin create(CreateAdminCommand command) {
        return createAdminCommandHandler.handle(command);
    }


}
