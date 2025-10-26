package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.entity.Receta;
import java.util.List;

public interface RecetaService {

    Receta guardarReceta(Receta Receta) throws Exception;
    List<Receta> obtenerTodas();
    Receta obtenerPorId(Long id);
    List<Receta> listarRecetaPorFechaCreacionDesc();
    Receta actualizar(Long id, Receta receta);
}
