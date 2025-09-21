package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Receta;

import java.util.List;

/**
 *
 * @author JAVIER-CUERVO
 */
public interface RecetaService {
    Receta crearReceta(String citaId, String medicamentoId, String dosis, String indicaciones);
    List<Receta> obtenerRecetasPorCita(String citaId);
    List<Receta> obtenerTodas();
}