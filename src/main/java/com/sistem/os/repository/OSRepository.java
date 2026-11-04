package com.sistem.os.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sistem.os.entity.OS;
import com.sistem.os.enums.StatusOS;
import com.sistem.os.enums.Prioridade;

@Repository
public interface OSRepository extends JpaRepository<OS, Long> {

    List<OS> findByStatus(StatusOS status);

    List<OS> findByClienteId(long cliente_id);

    List<OS> findByPrioridade(Prioridade prioridade);
}
