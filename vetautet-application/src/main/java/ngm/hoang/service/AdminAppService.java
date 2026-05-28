package ngm.hoang.service;

import ngm.hoang.model.Admin;

import ngm.hoang.cqrs.admin.command.CreateAdminCommand;

public interface AdminAppService {
    Admin create(CreateAdminCommand command);
}
