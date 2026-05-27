package ngm.hoang.usecase.command;

public record CreateAdminCommand(
        String username,
        String password
) {
}
