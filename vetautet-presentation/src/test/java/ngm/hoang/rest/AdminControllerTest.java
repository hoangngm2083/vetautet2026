package ngm.hoang.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import ngm.hoang.model.Admin;
import ngm.hoang.rest.request.CreateAdminRequest;
import ngm.hoang.usecase.AdminUseCase;
import ngm.hoang.usecase.command.CreateAdminCommand;
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
    private AdminUseCase useCase;

    @Test
    void create_ShouldReturnCreatedAdmin_WhenRequestIsValid() throws Exception {
        CreateAdminRequest request = new CreateAdminRequest("adminUser", "securePassword123");
        UUID adminId = UUID.randomUUID();
        Admin mockAdmin = Admin.builder()
                .id(adminId)
                .username("adminUser")
                .passwordHash("hashedPassword")
                .build();

        when(useCase.create(any(CreateAdminCommand.class))).thenReturn(mockAdmin);

        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(adminId.toString()))
                .andExpect(jsonPath("$.username").value("adminUser"));
    }

    @Test
    void create_ShouldReturnBadRequest_WhenUsernameIsBlank() throws Exception {
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
    void create_ShouldReturnBadRequest_WhenPasswordIsBlank() throws Exception {
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
    void create_ShouldReturnBadRequest_WhenUseCaseThrowsIllegalArgumentException() throws Exception {
        // Arrange: Giả lập trường hợp dữ liệu hợp lệ về mặt cú pháp nhưng vi phạm logic (ví dụ: trùng username)
        CreateAdminRequest request = new CreateAdminRequest("duplicateAdmin", "securePassword123");

        // Khi tầng usecase bị gọi, nó sẽ ném ra IllegalArgumentException giống như logic thực tế của bạn
        when(useCase.create(any(CreateAdminCommand.class)))
                .thenThrow(new IllegalArgumentException("Username 'duplicateAdmin' already exists"));

        // Act & Assert
        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                // Kiểm tra xem hàm handleIllegalArgumentException trong handler có hoạt động không
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.detail").value("Username 'duplicateAdmin' already exists"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}