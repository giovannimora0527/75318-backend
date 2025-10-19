package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author jartunduaga
 */

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findById(String id);
    Optional<Paciente> findByNumeroDocumento(String numeroDocumento);
    boolean existsByNumeroDocumento(String numeroDocumento);
    boolean existsByEmail(String email);
    boolean existsByNumeroDocumentoAndIdNot(String numeroDocumento, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
}
