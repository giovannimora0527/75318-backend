package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author JAVIER-CUERVO
 */

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    List<Paciente> findByFechaNacimiento(String fecha_nacimiento);

    List<Paciente> findByData(String fecha_nacimiento);

    List<Paciente> findAllByFechaNacimientoDesc();
    
    Optional<Paciente> findByNumeroDocumento(String documento);

}
