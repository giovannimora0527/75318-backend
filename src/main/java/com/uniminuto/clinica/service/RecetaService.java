package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Interfaz de servicio para la gestión de recetas médicas en el sistema clínico.
 * Maneja las operaciones CRUD y consultas específicas relacionadas con
 * las prescripciones médicas asociadas a citas.
 *
 * @author crash
 * @version 1.0
 * @since 2025-09-20
 */
public interface RecetaService {

    /**
     * Lista todas las recetas registradas en el sistema sin aplicar orden específico.
     *
     * @return List<Receta> Lista completa de recetas en el sistema
     * @throws RuntimeException si ocurre un error al acceder a la base de datos
     */
    List<Receta> listarTodasRecetas();

    // @return List<Receta> Lista de recetas ordenada cronológicamente (más recientes primero)
    List<Receta> listarRecetasDesc();

    //**
    //     * Busca todas las recetas asociadas a una cita médica específica.
    //     *
    //     * @param citaId Long identificador único de la cita médica
    //     * @return List<Receta> Lista de recetas prescritas en la cita especificada
    //     * @throws BadRequestException si la cita no existe en el sistema
    //     */
    List<Receta> buscarPorCita(Long citaId) throws BadRequestException;

    /**
     * Busca todas las recetas que contienen un medicamento específico.
     *
     * @param medicamentoId Integer identificador único del medicamento
     * @return List<Receta> Lista de recetas que incluyen el medicamento especificado
     */
    List<Receta> buscarPorMedicamento(Integer medicamentoId);

    // /**
    //     * Busca una receta específica mediante su identificador único.
    //     *
    //     * @param id Long identificador único de la receta
    //     * @return Receta objeto que corresponde al ID proporcionado
    //     * @throws BadRequestException si no existe una receta con el ID especificado
    //     */
    Receta buscarRecetaPorId(Long id) throws BadRequestException;

    /* @param recetaRq RecetaRq objeto que contiene los datos de la nueva receta
     * @return RespuestaRs objeto que indica el resultado de la operación
     * @throws BadRequestException si los datos de entrada son inválidos o incompletos
     */
    RespuestaRs guardarReceta(RecetaRq recetaRq) throws BadRequestException;
}