package ngm.hoang.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import ngm.hoang.exception.AlreadyExistsDomainException;
import ngm.hoang.model.Admin;
import ngm.hoang.rest.request.CreateAdminRequest;
import ngm.hoang.service.AdminAppService;
import ngm.hoang.cqrs.admin.command.CreateAdminCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdminController.class)
@ContextConfiguration(classes = WebMvcTestConfig.class)
@Import(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AdminAppService appService;

    @Test
    @DisplayName("create: validRequest -> 201 + id + username")
    void create_validRequest_created() throws Exception {
        CreateAdminRequest request = new CreateAdminRequest("adminUser", "securePassword123");
        UUID adminId = UUID.randomUUID();
        Admin mockAdmin = Admin.builder()
                .id(adminId)
                .username("adminUser")
                .passwordHash("hashedPassword")
                .build();

        when(appService.create(any(CreateAdminCommand.class))).thenReturn(mockAdmin);

        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(adminId.toString()))
                .andExpect(jsonPath("$.username").value("adminUser"));
    }

    @Test
    @DisplayName("create: blankUsername -> 400 ProblemDetail.invalidParams.username")
    void create_blankUsername_badRequest() throws Exception {
        // Arrange
        CreateAdminRequest request = new CreateAdminRequest("", "securePassword123");

        // Act & Assert
        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                // Kiểm tra xem GlobalExceptionHandler có thực sự can thiệp và trả về ProblemDetail không
                .andExpect(jsonPath("$.title").value("Invalid Request Content"))
                .andExpect(jsonPath("$.detail").value("Validation failed for one or more fields"))
                .andExpect(jsonPath("$.invalidParams.username").exists()) // Check xem có chỉ mặt đặt tên trường lỗi không
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("create: blankPassword -> 400 ProblemDetail.invalidParams.password")
    void create_blankPassword_badRequest() throws Exception {
        // Arrange
        CreateAdminRequest request = new CreateAdminRequest("adminUser", "");

        // Act & Assert
        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Invalid Request Content"))
                .andExpect(jsonPath("$.invalidParams.password").exists());
    }

    @Test
    @DisplayName("create: duplicateUsername -> 409 ProblemDetail (DomainException)")
    void create_duplicateUsername_conflict() throws Exception {
        CreateAdminRequest request = new CreateAdminRequest("duplicateAdmin", "securePassword123");

        when(appService.create(any(CreateAdminCommand.class)))
                .thenThrow(new AlreadyExistsDomainException(Admin.class, "username", "duplicateAdmin"));

        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Conflict"))
                .andExpect(jsonPath("$.detail").value("Already exists in system!"))
                .andExpect(jsonPath("$.model").value("Admin"))
                .andExpect(jsonPath("$.field").value("username"))
                .andExpect(jsonPath("$.value").value("duplicateAdmin"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}