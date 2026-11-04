package com.sistem.os.dto.cliente;

import com.sistem.os.entity.Cliente;

public record ClienteResponseDTO(Long id, String nome, String cpf, String email, String telefone) {
    public static ClienteResponseDTO from(Cliente c) {
        return new ClienteResponseDTO(c.getId(), c.getNome(), c.getCpf(), c.getEmail(), c.getTelefone());
    }
}
