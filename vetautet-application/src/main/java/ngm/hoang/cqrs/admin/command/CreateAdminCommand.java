package ngm.hoang.cqrs.admin.command;

import lombok.Builder;

@Builder
public record CreateAdminCommand(
        String username,
        String password
) {
}
