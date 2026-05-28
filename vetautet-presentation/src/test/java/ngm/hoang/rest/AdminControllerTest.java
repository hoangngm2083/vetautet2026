package ngm.hoang.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import ngm.hoang.cqrs.admin.query.GetAdminByIdQuery;
import ngm.hoang.dto.AdminDTO;
import ngm.hoang.exception.AlreadyExistsDomainException;
import ngm.hoang.exception.NotFoundDomainException;
import ngm.hoang.model.Admin;
import ngm.hoang.rest.mapper.AdminPresentationMapperImpl;
import ngm.hoang.rest.request.CreateAdminRequest;
import ngm.hoang.service.AdminAppService;
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

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdminController.class)
@ContextConfiguration(classes = WebMvcTestConfig.class)
@Import({
        AdminController.class,
        AdminPresentationMapperImpl.class
})
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AdminAppService appService;

    @Test
    @DisplayName("create: validRequest -> 201 envelope with id")
    void create_validRequest_created() throws Exception {
        CreateAdminRequest request = new CreateAdminRequest("adminUser", "securePassword123");
        UUID adminId = UUID.randomUUID();

        when(appService.create(any())).thenReturn(adminId);

        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Admin created successfully"))
                .andExpect(jsonPath("$.data.id").value(adminId.toString()))
                .andExpect(jsonPath("$.errors").value(nullValue()));
    }

    @Test
    @DisplayName("create: blankUsername -> 400 envelope with errors")
    void create_blankUsername_badRequest() throws Exception {
        CreateAdminRequest request = new CreateAdminRequest("", "securePassword123");

        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed for one or more fields"))
                .andExpect(jsonPath("$.errors.username").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("create: blankPassword -> 400 envelope with errors")
    void create_blankPassword_badRequest() throws Exception {
        CreateAdminRequest request = new CreateAdminRequest("adminUser", "");

        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.password").exists());
    }

    @Test
    @DisplayName("create: duplicateUsername -> 409 envelope")
    void create_duplicateUsername_conflict() throws Exception {
        CreateAdminRequest request = new CreateAdminRequest("duplicateAdmin", "securePassword123");

        when(appService.create(any()))
                .thenThrow(new AlreadyExistsDomainException(Admin.class, "username", "duplicateAdmin"));

        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message").value("Already exists in system!"))
                .andExpect(jsonPath("$.errors.model").value("Admin"))
                .andExpect(jsonPath("$.errors.field").value("username"))
                .andExpect(jsonPath("$.errors.value").value("duplicateAdmin"));
    }

    @Test
    @DisplayName("get: existingAdmin -> 200 envelope with admin data")
    void get_existingAdmin_ok() throws Exception {
        UUID adminId = UUID.randomUUID();
        AdminDTO dto = new AdminDTO(adminId, "adminUser");

        when(appService.get(any(GetAdminByIdQuery.class))).thenReturn(dto);

        mockMvc.perform(get("/admin/{id}", adminId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Admin retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(adminId.toString()))
                .andExpect(jsonPath("$.data.username").value("adminUser"))
                .andExpect(jsonPath("$.errors").value(nullValue()));
    }

    @Test
    @DisplayName("get: notFound -> 404 envelope")
    void get_notFound_notFound() throws Exception {
        UUID adminId = UUID.randomUUID();

        when(appService.get(any(GetAdminByIdQuery.class)))
                .thenThrow(new NotFoundDomainException(Admin.class, "id", adminId.toString()));

        mockMvc.perform(get("/admin/{id}", adminId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Resource not found"))
                .andExpect(jsonPath("$.errors.model").value("Admin"))
                .andExpect(jsonPath("$.errors.field").value("id"));
    }
}
