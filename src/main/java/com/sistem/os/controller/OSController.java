package com.sistem.os.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sistem.os.dto.os.OSRequestDTO;
import com.sistem.os.dto.os.OSResponseDTO;
import com.sistem.os.entity.OS;
import com.sistem.os.enums.Prioridade;
import com.sistem.os.enums.StatusOS;
import com.sistem.os.service.OSService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/OS")
public class OSController {
    private final OSService osService;

    public OSController(OSService osService) {
        this.osService = osService;
    }

    @GetMapping
    public List<OSResponseDTO> listar(@RequestParam(required = false) StatusOS statusOS,
            @RequestParam(required = false) Prioridade prioridade) {
        if (statusOS != null) {
            return osService.listPorStatus(statusOS)
                .stream()
                .map(OSResponseDTO::from)
                .toList();
        } else if (prioridade != null) {
            return osService.listarPorPrioridade(prioridade)
                .stream()
                .map(OSResponseDTO::from)
                .toList();
        }
        return osService.listarTodas()
            .stream()
            .map(OSResponseDTO::from)
            .toList();
    }

    @GetMapping("/{id}")
    public OSResponseDTO buscar(@PathVariable Long id) {
        return OSResponseDTO.from(osService.listPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public List<OSResponseDTO> listarPorCliente(@PathVariable Long clienteId) {
        return osService.listarPorCliente(clienteId)
            .stream()
            .map(OSResponseDTO::from)
            .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OSResponseDTO> abrirOs(@RequestBody @Valid OSRequestDTO os) {
        OS resposta = osService.abrirOs(os);
        return ResponseEntity.status(201).body(OSResponseDTO.from(resposta));
    }

    @PutMapping("/{id}/tecnico/{tecnicoId}")
    public OSResponseDTO atribuirTecnico(@PathVariable Long id, @PathVariable Long tecnicoId) {
        return OSResponseDTO.from(osService.atribuirTecnico(id, tecnicoId));
    }

    @PutMapping("/{id}/concluir")
    public OSResponseDTO concluirOs(@PathVariable Long id, @RequestParam(required = false) BigDecimal valor) {
        return OSResponseDTO.from(osService.concluirOs(id, valor));
    }

    @PutMapping("/{id}/cancelar")
    public OSResponseDTO cancelarOS(@PathVariable Long id) {
        return OSResponseDTO.from(osService.cancelarOs(id));
    }
}