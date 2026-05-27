package ngm.hoang.rest.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateAdminResponse(UUID id, String username) {
}
