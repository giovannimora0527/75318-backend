package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 1079977_JavierCuervo
 */

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>{
    List<Paciente> findByRol(String rol);
    
    Optional<Paciente> findByNumeroDocumento(String numero_documento);

   List<Paciente> findByDocument(String numero_documento);
        
    
}
