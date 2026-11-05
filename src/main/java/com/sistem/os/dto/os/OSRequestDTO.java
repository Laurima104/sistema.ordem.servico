package com.sistem.os.dto.os;

import com.sistem.os.enums.Prioridade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OSRequestDTO(
        @NotBlank String descricao,
        @NotNull Prioridade prioridade,
        @NotNull Long idCliente) {
}
