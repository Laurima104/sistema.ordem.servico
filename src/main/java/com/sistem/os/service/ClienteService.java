package com.sistem.os.service;

import com.sistem.os.entity.Cliente;
import com.sistem.os.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente não encontrado: " + id));
    }

    // REGRA 1: não pode existir dois clientes com o mesmo CPF
    public Cliente salvar(Cliente cliente) {
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Já existe cliente com este CPF");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente dados) {
        Cliente cliente = buscarPorId(id);
        cliente.setNome(dados.getNome());
        cliente.setTelefone(dados.getTelefone());
        cliente.setEmail(dados.getEmail());
        return clienteRepository.save(cliente);
    }

    public void excluir(Long id) {
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
    }

}
