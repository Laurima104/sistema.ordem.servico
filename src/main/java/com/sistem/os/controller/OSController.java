package com.sistem.os.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sistem.os.entity.OS;
import com.sistem.os.enums.StatusOS;
import com.sistem.os.service.OSService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/OS")
public class OSController {
    private final OSService osService;

    public OSController(OSService osService) {
        this.osService = osService;
    }

    @GetMapping
    public List<OS> listar(@RequestParam(required = false) StatusOS statusOS) {
        if (statusOS != null) {
            return osService.listPorStatus(statusOS);
        }
        return osService.listarTodas();
    }

    @GetMapping("/{id}")
    public OS buscar(@PathVariable Long osId) {
        return osService.listPorId(osId);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<OS> listarPorCliente(@PathVariable Long clienteId) {
        return osService.listarPorCliente(clienteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OS abrirOs(@RequestBody OS os) {
        return osService.abrirOs(os);
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