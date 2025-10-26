package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Especializacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * 
 * @author lmora
 */
@Repository
public interface EspecializacionRepository extends JpaRepository<Especializacion, Long> {

    // Método para encontrar especialización por código
    Optional<Especializacion> findByCodigoEspecializacion(String codigo);

    // Método para verificar si existe una especialización por código
    boolean existsByCodigoEspecializacion(String codigo);
}
