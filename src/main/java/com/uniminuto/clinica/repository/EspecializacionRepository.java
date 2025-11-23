package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Especializacion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EspecializacionRepository 
        extends JpaRepository<Especializacion, Long> {
    
    Optional<Especializacion> findByCodigoEspecializacion(String codigo);
    

    Optional<Especializacion> findByCodigoEspecializacionAndIdNot(String codigo, Long id); 
    

    List<Especializacion> findAllByOrderByCodigoEspecializacionAsc();
    

    List<Especializacion> findAllByOrderByCodigoEspecializacionDesc();
}