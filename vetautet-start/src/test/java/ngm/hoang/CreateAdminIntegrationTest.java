package ngm.hoang;

import ngm.hoang.rest.common.ApiResponse;
import ngm.hoang.rest.request.CreateAdminRequest;
import ngm.hoang.rest.response.AdminResponse;
import ngm.hoang.rest.response.IdResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

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
    @DisplayName("POST /admin: validRequest -> 201 envelope with id")
    void create_validRequest_created() {
        CreateAdminRequest request = new CreateAdminRequest("integrationUser", "superSecret123");

        ResponseEntity<ApiResponse<IdResponse>> response = restTemplate.exchange(
                "/admin",
                HttpMethod.POST,
                new org.springframework.http.HttpEntity<>(request),
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ApiResponse<IdResponse> body = response.getBody();
        assertNotNull(body);
        assertEquals(201, body.status());
        assertNotNull(body.data());
        assertNotNull(body.data().id());
    }

    @Test
    @DisplayName("POST then GET /admin/{id}: returns created admin")
    void createThenGet_returnsAdmin() {
        CreateAdminRequest request = new CreateAdminRequest("integrationGetUser", "superSecret123");

        ResponseEntity<ApiResponse<IdResponse>> createResponse = restTemplate.exchange(
                "/admin",
                HttpMethod.POST,
                new org.springframework.http.HttpEntity<>(request),
                new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        UUID adminId = createResponse.getBody().data().id();

        ResponseEntity<ApiResponse<AdminResponse>> getResponse = restTemplate.exchange(
                "/admin/" + adminId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        ApiResponse<AdminResponse> getBody = getResponse.getBody();
        assertNotNull(getBody);
        assertEquals(200, getBody.status());
        assertEquals(adminId, getBody.data().id());
        assertEquals("integrationGetUser", getBody.data().username());
    }

    @Test
    @DisplayName("GET /admin/{id}: unknown id -> 404")
    void get_unknownId_notFound() {
        UUID unknownId = UUID.randomUUID();

        ResponseEntity<ApiResponse<Void>> response = restTemplate.exchange(
                "/admin/" + unknownId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().status());
    }

    @Test
    @DisplayName("POST /admin: duplicateUsername -> 409 Conflict")
    void create_duplicateUsername_conflict() {
        CreateAdminRequest request = new CreateAdminRequest("integrationDupUser", "superSecret123");

        ResponseEntity<ApiResponse<IdResponse>> first = restTemplate.exchange(
                "/admin",
                HttpMethod.POST,
                new org.springframework.http.HttpEntity<>(request),
                new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.CREATED, first.getStatusCode());

        ResponseEntity<ApiResponse<Void>> second = restTemplate.exchange(
                "/admin",
                HttpMethod.POST,
                new org.springframework.http.HttpEntity<>(request),
                new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.CONFLICT, second.getStatusCode());
        assertEquals(409, second.getBody().status());
    }

    @Test
    @DisplayName("POST /admin: blankUsername -> 400 BadRequest")
    void create_blankUsername_badRequest() {
        CreateAdminRequest request = new CreateAdminRequest("", "superSecret123");

        ResponseEntity<ApiResponse<Void>> response = restTemplate.exchange(
                "/admin",
                HttpMethod.POST,
                new org.springframework.http.HttpEntity<>(request),
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().status());
    }
}
