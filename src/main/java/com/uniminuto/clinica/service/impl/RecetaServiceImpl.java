package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.MedicamentoRepository;
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

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Override
    public Receta guardarReceta(RecetaRq recetaRq) {
        if (recetaRq.getCitaId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo citaId es obligatorio.");
        }

        if (recetaRq.getMedicamentoId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo medicamentoId es obligatorio.");
        }

        Cita cita = citaRepository.findById(recetaRq.getCitaId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "La cita con ID " + recetaRq.getCitaId() + " no existe"));

        Medicamento medicamento = medicamentoRepository.findById(recetaRq.getMedicamentoId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "El medicamento con ID " + recetaRq.getMedicamentoId() + " no existe"));

        Receta receta = new Receta();
        receta.setCita(cita);
        receta.setMedicamentoId(recetaRq.getMedicamentoId());
        receta.setDosis(recetaRq.getDosis());
        receta.setIndicaciones(recetaRq.getIndicaciones());

        return recetaRepository.save(receta);
    }

    @Override
    public Receta actualizarReceta(Long id, RecetaRq recetaRq) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID de la receta es obligatorio.");
        }

        if (recetaRq.getCitaId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo citaId es obligatorio.");
        }

        if (recetaRq.getMedicamentoId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo medicamentoId es obligatorio.");
        }

        // Verificar que la receta existe
        Receta recetaExistente = recetaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "La receta con ID " + id + " no existe"));

        // Verificar que la cita existe
        Cita cita = citaRepository.findById(recetaRq.getCitaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "La cita con ID " + recetaRq.getCitaId() + " no existe"));

        // Verificar que el medicamento existe
        Medicamento medicamento = medicamentoRepository.findById(recetaRq.getMedicamentoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El medicamento con ID " + recetaRq.getMedicamentoId() + " no existe"));

        // Actualizar los campos
        recetaExistente.setCita(cita);
        recetaExistente.setMedicamentoId(recetaRq.getMedicamentoId());
        recetaExistente.setDosis(recetaRq.getDosis());
        recetaExistente.setIndicaciones(recetaRq.getIndicaciones());

        return recetaRepository.save(recetaExistente);
    }

    @Override
    public void eliminarReceta(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID de la receta es obligatorio.");
        }

        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "La receta con ID " + id + " no existe"));

        recetaRepository.delete(receta);
    }

    @Override
    public Receta buscarPorId(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID de la receta es obligatorio.");
        }

        return recetaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "La receta con ID " + id + " no existe"));
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
