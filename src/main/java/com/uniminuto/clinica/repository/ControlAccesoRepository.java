package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.ControlAcceso;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControlAccesoRepository extends JpaRepository<ControlAcceso, Long> {
    
    Optional<ControlAcceso> findByUsername(String username);
}