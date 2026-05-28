package ngm.hoang.service.impl;

import ngm.hoang.cqrs.admin.command.CreateAdminCommand;
import ngm.hoang.cqrs.admin.command.handler.CreateAdminCommandHandler;
import ngm.hoang.model.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminAppServiceImplTest {

    @Mock
    private CreateAdminCommandHandler createAdminCommandHandler;

    @InjectMocks
    private AdminAppServiceImpl adminAppService;

    private CreateAdminCommand command;
    private Admin mockAdmin;

    @BeforeEach
    void setUp() {
        command = new CreateAdminCommand("adminUser", "rawPassword123");
        mockAdmin = Admin.builder()
                .id(UUID.randomUUID())
                .username("adminUser")
                .passwordHash("hashedPassword123")
                .build();
    }

    @Test
    @DisplayName("create: delegate to handler and return Admin")
    void create_validCommand_admin() {
        when(createAdminCommandHandler.handle(command)).thenReturn(mockAdmin);

        Admin result = adminAppService.create(command);

        assertNotNull(result, "Resulting admin should not be null");
        assertEquals("adminUser", result.getUsername(), "Username should match");
        assertEquals("hashedPassword123", result.getPasswordHash(), "Password hash should match");

        verify(createAdminCommandHandler, times(1)).handle(command);
    }

    @Test
    @DisplayName("create: propagate exception from handler")
    void create_handlerThrows_exception() {
        when(createAdminCommandHandler.handle(command)).thenThrow(new RuntimeException("Handler error"));

        Exception ex = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> adminAppService.create(command)
        );
        assertEquals("Handler error", ex.getMessage());

        verify(createAdminCommandHandler, times(1)).handle(command);
    }
}
