package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author jartunduaga
 */

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findById(Paciente id);
}
