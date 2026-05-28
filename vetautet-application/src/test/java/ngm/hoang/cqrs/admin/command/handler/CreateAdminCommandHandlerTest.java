package ngm.hoang.cqrs.admin.command.handler;

import ngm.hoang.cqrs.admin.command.CreateAdminCommand;
import ngm.hoang.exception.AlreadyExistsDomainException;
import ngm.hoang.model.Admin;
import ngm.hoang.provider.Hasher;
import ngm.hoang.repository.AdminRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAdminCommandHandlerTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private Hasher hasher;

    @InjectMocks
    private CreateAdminCommandHandler handler;

    @Test
    @DisplayName("handle: validCommand -> returns saved admin id")
    void handle_validCommand_returnsId() {
        CreateAdminCommand command = new CreateAdminCommand("adminUser", "rawPassword123");

        when(adminRepository.existsByUsername("adminUser")).thenReturn(false);
        when(hasher.hash("rawPassword123")).thenReturn("hashedPassword123");
        when(adminRepository.save(any(Admin.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UUID result = handler.handle(command);

        assertNotNull(result);
    }

    @Test
    @DisplayName("handle: usernameExists -> throw AlreadyExistsDomainException")
    void handle_usernameExists_throwAlreadyExistsDomainException() {
        CreateAdminCommand command = new CreateAdminCommand("adminUser", "rawPassword123");

        when(adminRepository.existsByUsername("adminUser")).thenReturn(true);

        assertThrows(AlreadyExistsDomainException.class, () -> handler.handle(command));
    }

    @Test
    @DisplayName("handle: hasherThrows -> propagate exception")
    void handle_hasherThrows_exception() {
        CreateAdminCommand command = new CreateAdminCommand("adminUser", "rawPassword123");

        when(adminRepository.existsByUsername("adminUser")).thenReturn(false);
        when(hasher.hash("rawPassword123")).thenThrow(new IllegalStateException("Hashing failed"));

        Exception ex = assertThrows(IllegalStateException.class, () -> handler.handle(command));
        assertEquals("Hashing failed", ex.getMessage());
    }

    @Test
    @DisplayName("handle: repositorySaveThrows -> propagate exception")
    void handle_repositorySaveThrows_exception() {
        CreateAdminCommand command = new CreateAdminCommand("adminUser", "rawPassword123");

        when(adminRepository.existsByUsername("adminUser")).thenReturn(false);
        when(hasher.hash("rawPassword123")).thenReturn("hashedPassword123");
        when(adminRepository.save(any(Admin.class))).thenThrow(new RuntimeException("DB error"));

        Exception ex = assertThrows(RuntimeException.class, () -> handler.handle(command));
        assertEquals("DB error", ex.getMessage());
    }

    @Test
    @DisplayName("handle: nullCommand -> throw NullPointerException")
    void handle_nullCommand_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> handler.handle(null));
    }
}
