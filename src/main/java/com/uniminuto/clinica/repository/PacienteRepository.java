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
<<<<<<< HEAD
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    List<Paciente> findByFechaNacimiento(String fecha_nacimiento);

    List<Paciente> findByData(String fecha_nacimiento);

    List<Paciente> findAllByFechaNacimientoDesc();
=======
public interface PacienteRepository extends JpaRepository<Paciente, Long>{
    
    Optional<Paciente> findByNumeroDocumento(String numero_documento);

   List<Paciente> findByDocumento(String numero_documento);
        
>>>>>>> 6ddb292738158152aa065f4d15449b4ce3a7c0c8
    
    Optional<Paciente> findByNumeroDocumento(String documento);

}
