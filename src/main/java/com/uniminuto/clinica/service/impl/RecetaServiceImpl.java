package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.MedicamentoRepository;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.service.RecetaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecetaServiceImpl implements RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Override
    public List<Receta> listarRecetas() {
        return this.recetaRepository.findAll();
    }

    @Override
    public RespuestaRs guardarReceta(RecetaRq recetaRq) throws BadRequestException {
        // Validaciones de existencia de cita y medicamento (reutilizable)
        Optional<Medicamento> optMedicamento = this.medicamentoRepository.findById(recetaRq.getMedicamentoId());
        if (optMedicamento.isEmpty()) {
            throw new BadRequestException("El medicamento con ID " + recetaRq.getMedicamentoId() + " no existe.");
        }

        Optional<Cita> optCita = this.citaRepository.findById(recetaRq.getCitaId());
        if (optCita.isEmpty()) {
            throw new BadRequestException("La cita con ID " + recetaRq.getCitaId() + " no existe.");
        }

        // Verificar duplicados
        List<Receta> recetasExistentes = this.recetaRepository.findByCitaAndMedicamento(optCita.get(), optMedicamento.get());
        if (!recetasExistentes.isEmpty()) {
            throw new BadRequestException("Ya existe una receta para la cita con ID " + recetaRq.getCitaId() +
                    " y el medicamento con ID " + recetaRq.getMedicamentoId() + ".");
        }

        // Crear nueva entidad usando el método adaptado (pasa null para nueva)
        Receta receta = convertirRqAEntidad(recetaRq, optCita.get(), optMedicamento.get(), null);
        this.recetaRepository.save(receta);

        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Receta guardada exitosamente.");
        rta.setStatus(200);
        return rta;
    }

    @Override
    public RespuestaRs actualizarReceta(RecetaRq recetaRq) throws BadRequestException {

        // Validar que el ID esté presente
        if (recetaRq.getId() == null) {
            throw new BadRequestException("El ID de la receta es obligatorio para actualizar.");
        }

        // Recuperar la entidad existente

        Optional<Receta> optReceta = this.recetaRepository.findById(recetaRq.getId());
        if (optReceta.isEmpty()) {
            throw new BadRequestException("La receta con ID " + recetaRq.getId() + " no existe.");
        }


        Receta recetaActual = optReceta.get();
        if (!recetaActual.getMedicamento().getId().equals(recetaRq.getMedicamentoId())) {
            Optional<Medicamento> optMedicamento = this.medicamentoRepository.findById(recetaRq.getMedicamentoId());
            if (optMedicamento.isEmpty()) {
                throw new BadRequestException("El medicamento con ID " + recetaRq.getMedicamentoId() + " no existe.");
            }
            recetaActual.setMedicamento(optMedicamento.get());
        }

        recetaActual.setDosis(recetaRq.getDosis());
        recetaActual.setIndicaciones(recetaRq.getIndicaciones());
        recetaActual.setFechaActualizacionRegistro(LocalDateTime.now());
        this.recetaRepository.save(recetaActual);
        Receta recetaExistente = optReceta.get();

        // Validaciones de existencia de cita y medicamento (reutilizable)
        Optional<Medicamento> optMedicamento = this.medicamentoRepository.findById(recetaRq.getMedicamentoId());
        if (optMedicamento.isEmpty()) {
            throw new BadRequestException("El medicamento con ID " + recetaRq.getMedicamentoId() + " no existe.");
        }

        Optional<Cita> optCita = this.citaRepository.findById(recetaRq.getCitaId());
        if (optCita.isEmpty()) {
            throw new BadRequestException("La cita con ID " + recetaRq.getCitaId() + " no existe.");
        }

        // Verificar duplicados (excluyendo la receta actual)
        List<Receta> recetasExistentes = this.recetaRepository.findByCitaAndMedicamento(optCita.get(), optMedicamento.get());
        boolean isDuplicate = false;
        for (Receta r : recetasExistentes) {
            if (!r.getId().equals(recetaRq.getId())) {
                // Si hay otra receta con la misma cita y medicamento, es un duplicado
                isDuplicate = true;
                break;
            }
        }
        if (isDuplicate) {
            throw new BadRequestException("Ya existe una receta para la cita con ID " + recetaRq.getCitaId() +
                    " y el medicamento con ID " + recetaRq.getMedicamentoId() + ".");
        }

        // Actualizar la entidad existente usando el método adaptado (pasa la entidad existente)
        Receta recetaActualizada = convertirRqAEntidad(recetaRq, optCita.get(), optMedicamento.get(), recetaExistente);
        this.recetaRepository.save(recetaActualizada);

        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Receta actualizada exitosamente.");
        rta.setStatus(200);
        return rta;
    }

    /**
     * Convierte un objeto RecetaRq a una entidad Receta.
     * Si recetaExistente es null, crea una nueva entidad (para guardar).
     * Si recetaExistente no es null, actualiza la entidad existente (para actualizar).
     * Maneja actualizaciones parciales: solo actualiza campos no nulos/vacíos.
     * @param recetaRq receta de entrada.
     * @param cita la cita validada.
     * @param medicamento el medicamento validado.
     * @param recetaExistente entidad existente para actualizar (null para nueva).
     * @return entidad receta (nueva o actualizada).
     */
    private Receta convertirRqAEntidad(RecetaRq recetaRq, Cita cita, Medicamento medicamento, Receta recetaExistente) {
        Receta receta;
        if (recetaExistente != null) {
            // Actualizar entidad existente
            receta = recetaExistente;
            // No sobrescribir fechaCreacionRegistro en actualizaciones
        } else {
            // Crear nueva entidad
            receta = new Receta();
            receta.setFechaCreacionRegistro(LocalDateTime.now());
        }

        // Actualizar campos comunes (manejar parciales: solo si no son null/vacíos)
        if (cita != null) {
            receta.setCita(cita);
        }
        if (medicamento != null) {
            receta.setMedicamento(medicamento);
        }
        if (recetaRq.getDosis() != null && !recetaRq.getDosis().trim().isEmpty()) {
            receta.setDosis(recetaRq.getDosis());
        }
        if (recetaRq.getIndicaciones() != null && !recetaRq.getIndicaciones().trim().isEmpty()) {
            receta.setIndicaciones(recetaRq.getIndicaciones());
        }

        return receta;
    }
}

