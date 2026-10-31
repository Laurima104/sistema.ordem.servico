package com.sistem.os.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.sistem.os.entity.Tecnico;
import com.sistem.os.service.TecnicoService;
import java.util.List;

import org.springframework.http.HttpStatus;
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
    public List<Tecnico> listar() {
        return tecnicoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Tecnico buscar(@PathVariable Long id) {
        return tecnicoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tecnico cadastrar(@RequestBody Tecnico tecnico) {
        return tecnicoService.salvar(tecnico);
    }

    @PutMapping("/{id}")
    public Tecnico atualizar(@RequestBody Long id, @RequestBody Tecnico tecnico) {
        return tecnicoService.atualizar(id, tecnico);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        tecnicoService.excluir(id);
    }
}