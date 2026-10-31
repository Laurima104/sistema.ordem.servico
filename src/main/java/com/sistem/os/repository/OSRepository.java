package com.sistem.os.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sistem.os.entity.OS;

@Repository
public interface OSRepository extends JpaRepository<OS, Long> {

}
