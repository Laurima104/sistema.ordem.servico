package com.sistem.os.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sistem.os.dto.os.OSRequestDTO;
import com.sistem.os.dto.os.OSResponseDTO;
import com.sistem.os.entity.Cliente;
import com.sistem.os.entity.OS;
import com.sistem.os.entity.Tecnico;
import com.sistem.os.enums.Prioridade;
import com.sistem.os.enums.StatusOS;
import com.sistem.os.repository.OSRepository;

@Service
public class OSService {
    private final OSRepository osRepository;
    private final ClienteService clienteService;
    private final TecnicoService tecnicoService;

    public OSService(OSRepository osRepository, ClienteService clienteService, TecnicoService tecnicoService) {
        this.osRepository = osRepository;
        this.clienteService = clienteService;
        this.tecnicoService = tecnicoService;
    }

    public List<OS> listarTodas() {
        return osRepository.findAll();
    }

    public List<OS> listarPorCliente(Long cliente_id) {
        return osRepository.findByClienteId(cliente_id);
    }

    public List<OS> listPorStatus(StatusOS statusOS) {
        return osRepository.findByStatus(statusOS);
    }

    public List<OS> listarPorPrioridade(Prioridade prioridade) {
        return osRepository.findByPrioridade(prioridade);
    }

    public OS listPorId(Long id) {
        return osRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ordem de Serviço não encotrada: " + id));
    }

    private Cliente validarClienteCadastrado(OSRequestDTO os) {
        if (os.idCliente() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Informe o ID do cliente");
        }
        return clienteService.buscarPorId(os.idCliente());
    }

    public OSResponseDTO abrirOs(OSRequestDTO osRequest) {
        Cliente cliente = validarClienteCadastrado(osRequest);
        OS os = new OS();
        os.setCliente(cliente);
        os.setDataAbertura(LocalDateTime.now());
        os.setStatus(StatusOS.ABERTA);
        return OSResponseDTO.from(osRepository.save(os));
    }

    private void validarOsNaoFinalizada(OS os) {
        if (os.getStatus() == StatusOS.CONCLUIDA || os.getStatus() == StatusOS.CANCELADA) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A OS " + os.getId() + " ja esta  " + os.getStatus());
        }
    }

    public OS atribuirTecnico(Long id, Long tecnicoId) {
        OS os = listPorId(id);
        validarOsNaoFinalizada(os);

        Tecnico tecnico = tecnicoService.buscarPorId(tecnicoId);
        os.setTecnico(tecnico);
        os.setStatus(StatusOS.EM_ANDAMENTO);
        return osRepository.save(os);
    }

    private void validarTecnicoAtribuido(OS os) {
        if (os.getTecnico() == null || os.getTecnico().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Atribua um Tecnico antes de Concluir");
        }
    }

    private void validarVlrMaiorQueZero(BigDecimal vlr) {
        if (!(vlr.compareTo(BigDecimal.ZERO) > 0)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O Valor da OS deve ser maior do que 0,00");
        }
    }

    public OS concluirOs(Long osId, BigDecimal valor) {
        OS os = listPorId(osId);
        validarOsNaoFinalizada(os);
        validarTecnicoAtribuido(os);
        validarVlrMaiorQueZero(valor);

        os.setValor(valor);
        os.setDataFechamento(LocalDateTime.now());
        os.setStatus(StatusOS.CONCLUIDA);
        return osRepository.save(os);
    }

    public OS cancelarOs(Long osId) {
        OS os = listPorId(osId);
        validarOsNaoFinalizada(os);

        os.setDataFechamento(LocalDateTime.now());
        os.setStatus(StatusOS.CANCELADA);
        return osRepository.save(os);
    }
}
