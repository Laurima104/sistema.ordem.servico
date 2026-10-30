package com.sistem.os.repository;

import com.sistem.os.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // usado pela Regra 1 (CPF único)
    boolean existsByCpf(String cpf);
}
