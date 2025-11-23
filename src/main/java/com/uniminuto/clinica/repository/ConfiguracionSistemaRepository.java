package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.ConfiguracionSistema;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracionSistemaRepository extends JpaRepository<ConfiguracionSistema, Long> {
    
    Optional<ConfiguracionSistema> findByClave(String clave);
}