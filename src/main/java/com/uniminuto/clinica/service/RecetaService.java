package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecetaService {
    Receta guardarReceta(RecetaRq recetaRq);
    List<Receta> listarPorCita(Long citaId);

    List<Receta> listaRecetas();

}
