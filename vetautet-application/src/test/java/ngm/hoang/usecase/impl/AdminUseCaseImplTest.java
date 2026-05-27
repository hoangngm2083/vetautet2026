package ngm.hoang.usecase.impl;

import ngm.hoang.model.Admin;
import ngm.hoang.provider.Hasher;
import ngm.hoang.repository.AdminRepository;
import ngm.hoang.service.AdminService;
import ngm.hoang.usecase.command.CreateAdminCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminUseCaseImplTest {

    @Mock
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private Hasher hasher;

    @InjectMocks
    private AdminUseCaseImpl adminUseCase;

    private Admin mockAdmin;
    private CreateAdminCommand command;

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
    void create_ShouldReturnSavedAdmin_WhenValidCommandProvided() {
        // Arrange
        when(hasher.hash("rawPassword123")).thenReturn("hashedPassword123");
        when(adminService.create("adminUser", "hashedPassword123")).thenReturn(mockAdmin);
        when(adminRepository.save(mockAdmin)).thenReturn(mockAdmin);

        // Act
        Admin result = adminUseCase.create(command);

        // Assert
        assertNotNull(result, "Resulting admin should not be null");
        assertEquals("adminUser", result.getUsername(), "Username should match");
        assertEquals("hashedPassword123", result.getPasswordHash(), "Password hash should match");

        verify(hasher, times(1)).hash("rawPassword123");
        verify(adminService, times(1)).create("adminUser", "hashedPassword123");
        verify(adminRepository, times(1)).save(mockAdmin);
    }

    @Test
    void create_ShouldPropagateException_WhenRepositoryFails() {
        // Arrange
        when(hasher.hash(anyString())).thenReturn("hashedPassword123");
        when(adminService.create(anyString(), anyString())).thenReturn(mockAdmin);
        when(adminRepository.save(any(Admin.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        try {
            adminUseCase.create(command);
        } catch (Exception e) {
            assertEquals("Database error", e.getMessage());
        }

        verify(adminRepository, times(1)).save(mockAdmin);
    }
}
