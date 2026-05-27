package ngm.hoang.usecase;

import ngm.hoang.model.Admin;

import ngm.hoang.usecase.command.CreateAdminCommand;

public interface AdminUseCase {
    Admin create(CreateAdminCommand command);
}
