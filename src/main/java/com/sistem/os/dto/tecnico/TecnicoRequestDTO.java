package com.sistem.os.dto.tecnico;

import jakarta.validation.constraints.NotBlank;

public record TecnicoRequestDTO(
        @NotBlank String nome,
        @NotBlank String especialidade,
        String telefone) {
}