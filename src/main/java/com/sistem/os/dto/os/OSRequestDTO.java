package com.sistem.os.dto.os;

import java.math.BigDecimal;

import com.sistem.os.enums.Prioridade;
import com.sistem.os.enums.StatusOS;

import jakarta.validation.constraints.NotBlank;

public record OSRequestDTO(
        @NotBlank String descricao,
        BigDecimal valor,
        @NotBlank StatusOS statusOS,
        @NotBlank Prioridade prioridade,
        @NotBlank Long idCliente,
        Long idTecnico) {
}
