package com.sistem.os.dto.os;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sistem.os.entity.OS;
import com.sistem.os.enums.Prioridade;
import com.sistem.os.enums.StatusOS;

public record OSResponseDTO(Long id, String descricao, LocalDateTime data_abertura, LocalDateTime data_fechamento, BigDecimal valor, StatusOS statusOS, Prioridade prioridade, String nome_cliente, String nome_tecnico) {
    public static OSResponseDTO from(OS os) {
        return new OSResponseDTO(
            os.getId(), 
            os.getDescricao(), 
            os.getDataAbertura(), 
            os.getDataFechamento(), 
            os.getValor(), 
            os.getStatus(), 
            os.getPrioridade(), 
            os.getCliente() != null ? os.getCliente().getNome() : null, 
            os.getTecnico() != null ? os.getTecnico().getNome() : null
        );
    }
}
