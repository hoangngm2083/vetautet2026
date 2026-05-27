package ngm.hoang.persistence.repository.adapter;

import ngm.hoang.model.Admin;
import ngm.hoang.persistence.JpaTestConfig;
import ngm.hoang.persistence.entity.AdminEntity;
import ngm.hoang.persistence.repository.jpa.AdminJpaRepository;
import ngm.hoang.utils.mapper.AdminMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = JpaTestConfig.class)
@Import({AdminRepositoryImpl.class, AdminMapperImpl.class})
class AdminRepositoryImplTest {
    @Autowired
    private AdminRepositoryImpl adminRepository;
    @Autowired
    private AdminJpaRepository jpaRepository;
    private Admin adminModel;

    @BeforeEach
    void setUp() {
        adminModel = Admin.builder()
                .id(UUID.randomUUID())
                .username("adminUser")
                .passwordHash("hashedPassword123")
                .build();
    }

    @Test
    void save_ShouldPersistAdminAndReturnModel() {
        // Arrange
        // (setUp provides adminModel)

        // Act
        Admin savedAdmin = adminRepository.save(adminModel);

        // Assert
        assertNotNull(savedAdmin, "Saved admin should not be null");
        assertEquals(adminModel.getId(), savedAdmin.getId(), "ID should match");
        assertEquals(adminModel.getUsername(), savedAdmin.getUsername(), "Username should match");

        // Verify in DB directly via JPA Repository
        Optional<AdminEntity> foundEntity = jpaRepository.findById(adminModel.getId());
        assertTrue(foundEntity.isPresent(), "Entity should exist in database");
        assertEquals("adminUser", foundEntity.get().getUsername(), "Username in DB should match");
    }
}
