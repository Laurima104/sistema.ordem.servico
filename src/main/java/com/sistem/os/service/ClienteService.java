package com.sistem.os.service;

import com.sistem.os.dto.cliente.ClienteResponseDTO;
import com.sistem.os.entity.Cliente;
import com.sistem.os.entity.OS;
import com.sistem.os.repository.ClienteRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private OSService osService;

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
    public Cliente salvar(ClienteResponseDTO cliente) {
        Cliente cli = new Cliente();
        cli.setNome(cliente.nome());
        cli.setCpf(cliente.cpf());
        cli.setEmail(cliente.email());
        cli.setTelefone(cliente.telefone());
        if (clienteRepository.existsByCpf(cli.getCpf())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Já existe cliente com este CPF");
        } else {
            return clienteRepository.save(cli);
        }
    }

    public Cliente atualizar(Long id, ClienteResponseDTO dados) {
        Cliente cliente = buscarPorId(id);
        cliente.setNome(dados.nome());
        cliente.setTelefone(dados.telefone());
        cliente.setEmail(dados.email());
        return clienteRepository.save(cliente);
    }

    private List<OS> validarClienteComOs(Long id) {
        return osService.listarPorCliente(id);
    }

    public void excluir(Long id) {
        List<OS> os = validarClienteComOs(id);
        Cliente cliente = buscarPorId(id);
        if (!os.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cliente ja possui vinculo com Ordens de Serviço");
        }
        clienteRepository.delete(cliente);
    }
}
