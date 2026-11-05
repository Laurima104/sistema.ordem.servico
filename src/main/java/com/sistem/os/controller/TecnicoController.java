package com.sistem.os.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sistem.os.dto.tecnico.TecnicoResponseDTO;
import com.sistem.os.entity.Tecnico;
import com.sistem.os.service.TecnicoService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoController {
    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @GetMapping()
    public List<TecnicoResponseDTO> listar() {
        return tecnicoService.listarTodos()
            .stream()
            .map(TecnicoResponseDTO::from)
            .toList();
    }

    @GetMapping("/{id}")
    public TecnicoResponseDTO buscar(@PathVariable Long id) {
        return TecnicoResponseDTO.from(tecnicoService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TecnicoResponseDTO> cadastrar(@RequestBody @Valid TecnicoResponseDTO tecnico) {
        Tecnico tec = tecnicoService.salvar(tecnico);
        return ResponseEntity.status(201).body(TecnicoResponseDTO.from(tec));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TecnicoResponseDTO> atualizar(@RequestBody Long id, @RequestBody @Valid TecnicoResponseDTO tecnico) {
        Tecnico tec = tecnicoService.atualizar(id, tecnico);
        return ResponseEntity.status(201).body(TecnicoResponseDTO.from(tec));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        tecnicoService.excluir(id);
    }
}