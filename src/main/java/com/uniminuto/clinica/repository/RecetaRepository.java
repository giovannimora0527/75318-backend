package com.uniminuto.clinica.repository;


import com.uniminuto.clinica.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author JAVIER-CUERVO
 */
@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    List<Receta> findByCitaId(String citaId);
}
