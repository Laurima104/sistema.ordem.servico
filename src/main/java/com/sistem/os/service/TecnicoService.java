package com.sistem.os.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sistem.os.dto.tecnico.TecnicoResponseDTO;
import com.sistem.os.entity.Tecnico;
import com.sistem.os.repository.TecnicoRepository;

@Service
public class TecnicoService {
    private final TecnicoRepository tecnicoRepository;

    public TecnicoService(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    public List<Tecnico> listarTodos() {
        return tecnicoRepository.findAll();
    }

    public Tecnico buscarPorId(Long id) {
        return tecnicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Tecnico não encontrado: " + id));
    }

    public Tecnico salvar(TecnicoResponseDTO tecnico) {
        Tecnico tec = new Tecnico();
        tec.setNome(tecnico.nome());
        tec.setEspecialidade(tecnico.especialidade());
        tec.setTelefone(tecnico.telefone());
        return tecnicoRepository.save(tec);
    }

    public Tecnico atualizar(Long id, TecnicoResponseDTO dados) {
        Tecnico tecnico = buscarPorId(id);
        tecnico.setNome(dados.nome());
        tecnico.setEspecialidade(dados.especialidade());
        tecnico.setTelefone(dados.telefone());
        return tecnicoRepository.save(tecnico);
    }

    public void excluir(Long id) {
        Tecnico tecnico = buscarPorId(id);
        tecnicoRepository.delete(tecnico);
    }
}