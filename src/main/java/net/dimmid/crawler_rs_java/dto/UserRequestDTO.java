package net.dimmid.crawler_rs_java.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "username is required")
        @Size(min = 3, max = 60, message = "username must be greater than 3 and lower than 60")
        String username,
        @NotBlank(message = "password is required")
        @Size(min = 1, message = "Password must be greater than 0")
        String password
) {
}
