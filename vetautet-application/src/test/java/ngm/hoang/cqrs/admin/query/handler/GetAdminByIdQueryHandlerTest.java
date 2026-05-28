package ngm.hoang.cqrs.admin.query.handler;

import ngm.hoang.cqrs.admin.query.GetAdminByIdQuery;
import ngm.hoang.dto.AdminDTO;
import ngm.hoang.exception.NotFoundDomainException;
import ngm.hoang.mapper.AdminDtoMapper;
import ngm.hoang.model.Admin;
import ngm.hoang.repository.AdminRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAdminByIdQueryHandlerTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private AdminDtoMapper adminDtoMapper;

    @InjectMocks
    private GetAdminByIdQueryHandler handler;

    @Test
    @DisplayName("handle: adminFound -> AdminDTO")
    void handle_adminFound_adminDto() {
        UUID id = UUID.randomUUID();
        GetAdminByIdQuery query = new GetAdminByIdQuery(id);
        Admin admin = Admin.builder().id(id).username("adminUser").passwordHash("hash").build();
        AdminDTO expected = new AdminDTO(id, "adminUser");

        when(adminRepository.findById(id)).thenReturn(Optional.of(admin));
        when(adminDtoMapper.toDto(admin)).thenReturn(expected);

        AdminDTO result = handler.handle(query);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("handle: adminNotFound -> NotFoundDomainException")
    void handle_adminNotFound_notFoundDomainException() {
        UUID id = UUID.randomUUID();
        GetAdminByIdQuery query = new GetAdminByIdQuery(id);

        when(adminRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundDomainException.class, () -> handler.handle(query));
    }
}
