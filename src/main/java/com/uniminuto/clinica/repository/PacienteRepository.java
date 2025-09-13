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
public interface PacienteRepository extends JpaRepository<Paciente, Long>{
    
    Optional<Paciente> findByNumeroDocumento(String numero_documento);

   List<Paciente> findByDocumento(String numero_documento);
        
    
}
