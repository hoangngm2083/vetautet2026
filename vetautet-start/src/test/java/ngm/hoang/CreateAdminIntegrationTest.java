package ngm.hoang;

import ngm.hoang.rest.request.CreateAdminRequest;
import ngm.hoang.rest.response.CreateAdminResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class CreateAdminIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createAdmin_ShouldReturnCreatedAndPersistToDb_WhenRequestIsValid() {
        // Arrange
        CreateAdminRequest request = new CreateAdminRequest("integrationUser", "superSecret123");

        // Act
        ResponseEntity<CreateAdminResponse> response = restTemplate.postForEntity(
                "/admin",
                request,
                CreateAdminResponse.class
        );

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Should return 201 Created");

        CreateAdminResponse body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertNotNull(body.id(), "Generated ID should be present");
        assertEquals("integrationUser", body.username(), "Username should match request");
    }
}
