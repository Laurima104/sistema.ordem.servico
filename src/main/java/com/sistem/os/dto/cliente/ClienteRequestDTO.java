package com.sistem.os.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(
        @NotBlank String nome,
        String email,
        @NotBlank @Size(min = 14) String cpf,
        @Size(min = 15) String telefone) {
}
