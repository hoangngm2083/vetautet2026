package ngm.hoang.service.impl;

import ngm.hoang.model.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AdminServiceImplTest {

    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        adminService = new AdminServiceImpl();
    }

    @Test
    void create_ShouldReturnAdminWithId_WhenValidInputsProvided() {
        // Arrange
        String username = "adminUser";
        String passwordHash = "hashedPassword123";

        // Act
        Admin admin = adminService.create(username, passwordHash);

        // Assert
        assertNotNull(admin, "Admin object should not be null");
        assertNotNull(admin.getId(), "Admin ID should be generated");
        assertEquals(username, admin.getUsername(), "Username should match input");
        assertEquals(passwordHash, admin.getPasswordHash(), "Password hash should match input");
    }
}
