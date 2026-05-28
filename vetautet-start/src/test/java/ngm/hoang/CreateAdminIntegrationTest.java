package ngm.hoang;

import ngm.hoang.rest.request.CreateAdminRequest;
import ngm.hoang.rest.response.CreateAdminResponse;
import org.junit.jupiter.api.DisplayName;
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
@Testcontainers(disabledWithoutDocker = true)
public class CreateAdminIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("POST /admin: validRequest -> 201 + responseBody")
    void create_validRequest_created() {
        CreateAdminRequest request = new CreateAdminRequest("integrationUser", "superSecret123");

        ResponseEntity<CreateAdminResponse> response = restTemplate.postForEntity(
                "/admin",
                request,
                CreateAdminResponse.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Should return 201 Created");

        CreateAdminResponse body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertNotNull(body.id(), "Generated ID should be present");
        assertEquals("integrationUser", body.username(), "Username should match request");
    }

    @Test
    @DisplayName("POST /admin: duplicateUsername -> 409 Conflict")
    void create_duplicateUsername_conflict() {
        CreateAdminRequest request = new CreateAdminRequest("integrationDupUser", "superSecret123");

        ResponseEntity<CreateAdminResponse> first = restTemplate.postForEntity(
                "/admin",
                request,
                CreateAdminResponse.class
        );
        assertEquals(HttpStatus.CREATED, first.getStatusCode());

        ResponseEntity<String> second = restTemplate.postForEntity(
                "/admin",
                request,
                String.class
        );
        assertEquals(HttpStatus.CONFLICT, second.getStatusCode());
    }

    @Test
    @DisplayName("POST /admin: blankUsername -> 400 BadRequest")
    void create_blankUsername_badRequest() {
        CreateAdminRequest request = new CreateAdminRequest("", "superSecret123");

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/admin",
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
