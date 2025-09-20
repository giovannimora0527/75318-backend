package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Receta;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecetaService {
    Receta guardarReceta(Long citaId, Receta receta);
    List<Receta> listarPorCita(Long citaId);
}
