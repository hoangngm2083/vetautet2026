package ngm.hoang.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ngm.hoang.model.Admin;
import ngm.hoang.rest.request.CreateAdminRequest;
import ngm.hoang.rest.response.CreateAdminResponse;
import ngm.hoang.usecase.AdminUseCase;
import ngm.hoang.usecase.command.CreateAdminCommand;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {
    private final AdminUseCase useCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAdminResponse create(@Valid @RequestBody CreateAdminRequest request) {

        CreateAdminCommand command = new CreateAdminCommand(request.username(), request.password());
        Admin createdAdmin = useCase.create(command);

        // Tối ưu: Trả về DTO thay vì void
//        return new CreateAdminResponse(createdAdmin.getId(), createdAdmin.getUsername());
        return CreateAdminResponse.builder()
                .id(createdAdmin.getId())
                .username(createdAdmin.getUsername())
                .build();
    }
}

