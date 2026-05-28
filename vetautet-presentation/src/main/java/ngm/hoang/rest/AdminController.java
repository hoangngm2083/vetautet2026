package ngm.hoang.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ngm.hoang.dto.AdminDTO;
import ngm.hoang.rest.common.ApiResponse;
import ngm.hoang.rest.mapper.AdminPresentationMapper;
import ngm.hoang.rest.request.CreateAdminRequest;
import ngm.hoang.rest.request.GetAdminByIdRequest;
import ngm.hoang.rest.response.AdminResponse;
import ngm.hoang.rest.response.IdResponse;
import ngm.hoang.service.AdminAppService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {
    private final AdminAppService appService;
    private final AdminPresentationMapper adminPresentationMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<IdResponse> create(@Valid @RequestBody CreateAdminRequest request) {
        UUID id = appService.create(adminPresentationMapper.toCommand(request));
        return ApiResponse.success(HttpStatus.CREATED, "Admin created successfully", new IdResponse(id));
    }

    @GetMapping("/{id}")
    public ApiResponse<AdminResponse> get(@PathVariable("id") UUID id) {
        AdminDTO dto = appService.get(adminPresentationMapper.toQuery(new GetAdminByIdRequest(id)));
        return ApiResponse.success(
                HttpStatus.OK,
                "Admin retrieved successfully",
                adminPresentationMapper.toResponse(dto)
        );
    }
}
