package ngm.hoang.service;

import ngm.hoang.cqrs.admin.command.CreateAdminCommand;
import ngm.hoang.cqrs.admin.query.GetAdminByIdQuery;
import ngm.hoang.dto.AdminDTO;

import java.util.UUID;

public interface AdminAppService {
    UUID create(CreateAdminCommand command);

    AdminDTO get(GetAdminByIdQuery query);
}
