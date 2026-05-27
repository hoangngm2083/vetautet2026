package ngm.hoang.rest.request;


import jakarta.validation.constraints.NotBlank;

public record CreateAdminRequest(
        @NotBlank(message = "Username is required") String username,
        @NotBlank(message = "Password is required") String password) {
}
