package ngm.hoang.service.impl;

import ngm.hoang.cqrs.admin.command.CreateAdminCommand;
import ngm.hoang.cqrs.admin.command.handler.CreateAdminCommandHandler;
import ngm.hoang.cqrs.admin.query.GetAdminByIdQuery;
import ngm.hoang.cqrs.admin.query.handler.GetAdminByIdQueryHandler;
import ngm.hoang.dto.AdminDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminAppServiceImplTest {

    @Mock
    private CreateAdminCommandHandler createAdminCommandHandler;

    @Mock
    private GetAdminByIdQueryHandler getAdminByIdQueryHandler;

    @InjectMocks
    private AdminAppServiceImpl adminAppService;

    private CreateAdminCommand command;
    private UUID adminId;
    private AdminDTO adminDto;

    @BeforeEach
    void setUp() {
        command = new CreateAdminCommand("adminUser", "rawPassword123");
        adminId = UUID.randomUUID();
        adminDto = new AdminDTO(adminId, "adminUser");
    }

    @Test
    @DisplayName("create: delegate to command handler and return UUID")
    void create_validCommand_uuid() {
        when(createAdminCommandHandler.handle(command)).thenReturn(adminId);

        UUID result = adminAppService.create(command);

        assertEquals(adminId, result);
        verify(createAdminCommandHandler, times(1)).handle(command);
    }

    @Test
    @DisplayName("create: propagate exception from handler")
    void create_handlerThrows_exception() {
        when(createAdminCommandHandler.handle(command)).thenThrow(new RuntimeException("Handler error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> adminAppService.create(command));
        assertEquals("Handler error", ex.getMessage());

        verify(createAdminCommandHandler, times(1)).handle(command);
    }

    @Test
    @DisplayName("get: delegate to query handler and return AdminDTO")
    void get_validQuery_adminDto() {
        GetAdminByIdQuery query = new GetAdminByIdQuery(adminId);
        when(getAdminByIdQueryHandler.handle(query)).thenReturn(adminDto);

        AdminDTO result = adminAppService.get(query);

        assertEquals(adminDto, result);
        verify(getAdminByIdQueryHandler, times(1)).handle(query);
    }
}
