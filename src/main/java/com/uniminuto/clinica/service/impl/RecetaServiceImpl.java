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

    /**
     * Repositorio de datos para recetas médicas.
     */
    @Autowired
    private RecetaRepository recetaRepository;

    /**
     * CitaRepository.
     */
    @Autowired
    private CitaRepository citaRepository;

    /**
     * MedicamentoRepository.
     */
    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Override
    public List<Receta> listarTodasLasRecetas() {
        return this.recetaRepository.findAll();
    }

    @Override
    public RespuestaRs guardarReceta(RecetaRq recetaRq) throws BadRequestException {
        Optional<Medicamento> optMedicamento = this.medicamentoRepository.findById(recetaRq.getMedicamento());
        if (optMedicamento.isEmpty()) {
            throw new BadRequestException("El medicamento con ID " + recetaRq.getMedicamento() + " no existe.");
        }

        Optional<Cita> optCita = this.citaRepository.findById(recetaRq.getCita());
        if(optCita.isEmpty()) {
            throw new BadRequestException("La cita con ID " + recetaRq.getCita() + " no existe.");
        }

        Cita cita = optCita.get();
        Medicamento medicamento = optMedicamento.get();
        List<Receta> recetasExistentes = this.recetaRepository.findByCitaAndMedicamento(cita, medicamento);
        if (!recetasExistentes.isEmpty()) {
            throw new BadRequestException("Ya existe una receta para la cita con ID " + recetaRq.getCita() +
                    " y el medicamento con ID " + recetaRq.getMedicamento() + ".");
        }

        Receta receta = this.convertirRqAEntidad(recetaRq, optCita.get(), optMedicamento.get());
        this.recetaRepository.save(receta);
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Receta guardada exitosamente.");
        rta.setStatus(200);
        return rta;
    }

    /**
     * Convierte un objeto RecetaRq a una entidad Receta.
     * @param recetaRq receta de entrada.
     * @return entidad receta.
     */
    private Receta convertirRqAEntidad(RecetaRq recetaRq, Cita cita, Medicamento medicamento) {
        Receta receta = new Receta();
        receta.setCita(cita);
        receta.setMedicamento(medicamento);
        receta.setDosis(recetaRq.getDosis());
        receta.setIndicaciones(recetaRq.getIndicaciones());
        receta.setFechaCreacionRegistro(LocalDateTime.now());
        return receta;
    }

    public RespuestaRs actualizarReceta(RecetaRq recetaRq)
            throws BadRequestException {
        // Paso 1. Consultar si el campo id existe y viene en el request
        if (recetaRq.getId() == null) {
            throw new BadRequestException("El id de la receta es obligatoria");
        }
        // Paso 2. Consultar si el medicamento existe por id
        Optional<Receta> optReceta = recetaRepository
                .findById(recetaRq.getId());

        // Paso 3. Si no existe lanzo error
        Receta recetaActual = recetaRepository.findById(recetaRq.getId())
                .orElseThrow(() -> new BadRequestException("La receta no existe y no se puede actualizar"));

        // Paso 6. Si no existe por nombre, actualizo los datos del medicamento
        recetaActual.setDosis(recetaRq.getDosis() == null ? recetaActual.getDosis() : recetaRq.getDosis());
        recetaActual.setIndicaciones(recetaRq.getIndicaciones() == null ? recetaActual.getIndicaciones() : recetaRq.getIndicaciones());
        this.recetaRepository.save(recetaActual);

        // Paso 7. Retorno la respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Receta actualizada exitosamente");
        rta.setStatus(200);

        return rta;
    }

    @Override
    public RespuestaRs eliminarReceta(Integer idReceta) throws BadRequestException {

        Optional<Receta> optReceta = recetaRepository.findById(idReceta);

        if (optReceta.isEmpty()) {
            throw new BadRequestException("El receta no existe, no se puede eliminar");
        }

        Receta receta = optReceta.get();

        // Eliminamos el paciente
        recetaRepository.delete(receta);

        // Respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Receta eliminado correctamente");
        rta.setStatus(200);

        return rta;
    }

}
