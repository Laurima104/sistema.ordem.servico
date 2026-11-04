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
    public List<OS> listar(@RequestParam(required = false) StatusOS statusOS,
            @RequestParam(required = false) Prioridade prioridade) {
        if (statusOS != null) {
            return osService.listPorStatus(statusOS);
        } else if (prioridade != null) {
            return osService.listarPorPrioridade(prioridade);
        }
        return osService.listarTodas();
    }

    @GetMapping("/{id}")
    public OS buscar(@PathVariable Long id) {
        return osService.listPorId(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<OS> listarPorCliente(@PathVariable Long clienteId) {
        return osService.listarPorCliente(clienteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OSResponseDTO> abrirOs(@RequestBody @Valid OSRequestDTO os) {
        OSResponseDTO resposta = osService.abrirOs(os);
        return ResponseEntity.status(201).body(resposta);
    }

    @PutMapping("/{id}/tecnico/{tecnicoId}")
    public OS atribuirTecnico(@PathVariable Long id, @PathVariable Long tecnicoId) {
        return osService.atribuirTecnico(id, tecnicoId);
    }

    @PutMapping("/{id}/concluir")
    public OS concluirOs(@PathVariable Long id, @RequestParam(required = false) BigDecimal valor) {
        return osService.concluirOs(id, valor);
    }

    @PutMapping("/{id}/cancelar")
    public OS cancelarOS(@PathVariable Long id) {
        return osService.cancelarOs(id);
    }
}