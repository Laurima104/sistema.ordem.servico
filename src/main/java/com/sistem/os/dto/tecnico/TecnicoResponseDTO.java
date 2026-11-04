package com.sistem.os.dto.tecnico;

import com.sistem.os.entity.Tecnico;

public record TecnicoResponseDTO(Long id, String nome, String especialidade, String telefone) {
    public static TecnicoResponseDTO from(Tecnico tec) {
        return new TecnicoResponseDTO(tec.getId(), tec.getNome(), tec.getEspecialidade(), tec.getTelefone());
    }
}
