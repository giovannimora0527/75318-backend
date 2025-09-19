package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;

import java.util.List;

public interface RecetaService {
    RespuestaRs<Receta> guardarReceta(RecetaRq recetaRq);
    List<Receta> listarRecetas();
    List<Receta> listarRecetasOrdenadosPorFecha();

}
