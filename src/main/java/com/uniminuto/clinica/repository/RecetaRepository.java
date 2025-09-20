package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Receta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    // Buscar recetas por ID de cita
    List<Receta> findByCitaId(Long citaId);

    // Buscar recetas por medicamento
    List<Receta> findByMedicamentoId(Integer medicamentoId);

    // Buscar por ID (ya incluido en JpaRepository, pero para consistencia)
    Optional<Receta> findById(Long id);
}
