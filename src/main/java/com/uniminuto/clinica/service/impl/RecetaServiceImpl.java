package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author jartunduaga
 */
@Service
public class RecetaServiceImpl implements RecetaService {
    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public Receta guardarReceta(RecetaRq recetaRq) {
        if (recetaRq.getCitaId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo citaId es obligatorio.");
        }

        Cita cita = citaRepository.findById(recetaRq.getCitaId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "La cita con ID " + recetaRq.getCitaId() + " no existe"));

        Receta receta = new Receta();
        receta.setCita(cita);
        receta.setMedicamentoId(recetaRq.getMedicamentoId());
        receta.setDosis(recetaRq.getDosis());
        receta.setIndicaciones(recetaRq.getIndicaciones());

        return recetaRepository.save(receta);
    }

    @Override
    public List<Receta> listarPorCita(Long citaId) {
        return recetaRepository.findByCitaId(citaId);
    }

    @Override
    public List<Receta> listaRecetas() {
        return recetaRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaCreacionRegistro"));
    }
}
