package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author JAVIER-CUERVO
 */
@Service
public class RecetaServiceImpl implements RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Override
    public Receta crearReceta(String citaId, String medicamentoId, String dosis, String indicaciones) {
        Receta receta = new Receta();
        receta.setCitaId(citaId);
        receta.setMedicamentoId(medicamentoId);
        receta.setDosis(dosis);
        receta.setIndicaciones(indicaciones);
        receta.setFechaCreacionRegistro(LocalDateTime.now());

        return recetaRepository.save(receta);
    }

    @Override
    public List<Receta> obtenerRecetasPorCita(String citaId) {
        return recetaRepository.findByCitaId(citaId);
    }

    @Override
    public List<Receta> obtenerTodas() {
        return recetaRepository.findAll();
    }

}