package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

public interface RecetaService {

    // Listar todas las recetas
    List<Receta> listarTodasRecetas();

    // Listar recetas ordenadas por fecha de creación descendente (más recientes primero)
    List<Receta> listarRecetasDesc();

    // Buscar recetas por cita
    List<Receta> buscarPorCita(Long citaId) throws BadRequestException;

    // Buscar recetas por medicamento
    List<Receta> buscarPorMedicamento(Integer medicamentoId);

    // Buscar receta por ID
    Receta buscarRecetaPorId(Long id) throws BadRequestException;

    // Guardar nueva receta
    RespuestaRs guardarReceta(RecetaRq recetaRq) throws BadRequestException;
}