package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Receta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para operaciones de persistencia de la entidad Receta.
 * Extiende JpaRepository para heredar operaciones CRUD básicas y
 * define consultas personalizadas mediante query methods.
 *
 * Spring Data JPA genera automáticamente las implementaciones
 * basándose en los nombres de los métodos.
 *
 * @author crash
 * @version 1.0
 * @since 2025-09-20
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see Receta
 */
@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    /**
     * Busca todas las recetas asociadas a una cita específica.
     *
     * Spring Data JPA interpreta automáticamente este método como:
     * SELECT * FROM receta WHERE cita_id = ?
     *
     * @param citaId Long identificador de la cita médica
     * @return List<Receta> lista de recetas prescritas en la cita,
     *         puede ser vacía si no hay recetas asociadas
     /* @see Receta#getCita()
     */
    List<Receta> findByCitaId(Long citaId);

    /**
     * Busca todas las recetas que contienen un medicamento específico.
     *
     * Útil para análisis de prescripciones, control de inventario
     * y estadísticas de uso de medicamentos.
     *
     * Consulta generada automáticamente:
     * SELECT * FROM receta WHERE medicamento_id = ?
     *
     * @param medicamentoId Integer identificador del medicamento
     * @return List<Receta> lista de recetas que incluyen el medicamento,
     *         puede ser vacía si el medicamento no ha sido prescrito
     /* @see Receta#getMedicamentoId()
     */
    List<Receta> findByMedicamentoId(Integer medicamentoId);

    /**
     * Busca una receta por su identificador único.
     *
     * Aunque este método ya está incluido en JpaRepository como findById(),
     * se declara explícitamente para mantener consistencia con el patrón
     * de nomenclatura del repositorio.
     *
     * @param id Long clave primaria de la receta
     * @return Optional<Receta> contenedor que puede o no contener la receta
    / * @see java.util.Optional
    / * @see JpaRepository#findById(Object)
     */
    Optional<Receta> findById(Long id);
}
