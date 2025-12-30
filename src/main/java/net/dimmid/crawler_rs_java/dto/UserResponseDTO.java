package net.dimmid.crawler_rs_java.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserResponseDTO(
        Long id,
        @NotEmpty
        String username,
        Long charId
) {
}
