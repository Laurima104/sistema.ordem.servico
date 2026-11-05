package com.sistem.os.controller;

import com.sistem.os.dto.cliente.ClienteResponseDTO;
import com.sistem.os.entity.Cliente;
import com.sistem.os.service.ClienteService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // GET /clientes
    @GetMapping
    public List<ClienteResponseDTO> listar() {
        return clienteService.listarTodos()
            .stream()
            .map(ClienteResponseDTO::from)
            .toList();
    }

    // GET /clientes/1
    @GetMapping("/{id}")
    public ClienteResponseDTO buscar(@PathVariable Long id) {
        return ClienteResponseDTO.from(clienteService.buscarPorId(id));
    }

    // POST /clientes
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClienteResponseDTO> cadastrar(@RequestBody @Valid ClienteResponseDTO cliente) {
        Cliente cli = clienteService.salvar(cliente);
        return ResponseEntity.status(201).body(ClienteResponseDTO.from(cli));
    }

    // PUT /clientes/1
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ClienteResponseDTO cliente) {
        Cliente cli = clienteService.atualizar(id, cliente);
        return ResponseEntity.status(201).body(ClienteResponseDTO.from(cli));
    }

    // DELETE /clientes/1
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        clienteService.excluir(id);
    }

}
