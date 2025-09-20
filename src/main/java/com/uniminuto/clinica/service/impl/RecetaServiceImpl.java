package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.service.CitaService;
import com.uniminuto.clinica.service.RecetaService;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecetaServiceImpl implements RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private CitaService citaService;

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public List<Receta> listarTodasRecetas() {
        return recetaRepository.findAll();
    }

    @Override
    public List<Receta> listarRecetasDesc() {
        return recetaRepository.findAll()
                .stream()
                .sorted((r1, r2) -> r2.getFechaCreacionRegistro().compareTo(r1.getFechaCreacionRegistro()))
                .toList();
    }

    @Override
    public List<Receta> buscarPorCita(Long citaId) throws BadRequestException {

        return recetaRepository.findByCitaId(citaId);
    }

    @Override
    public List<Receta> buscarPorMedicamento(Integer medicamentoId) {
        return recetaRepository.findByMedicamentoId(medicamentoId);
    }

    @Override
    public Receta buscarRecetaPorId(Long id) throws BadRequestException {
        Optional<Receta> optReceta = recetaRepository.findById(id);
        if (!optReceta.isPresent()) {
            throw new BadRequestException("No existe la receta con el id: " + id);
        }
        return optReceta.get();
    }

    @Override
    public RespuestaRs guardarReceta(RecetaRq recetaRq) throws BadRequestException {
        // Paso 1: Validar campos obligatorios
        validarCampos(recetaRq);

        // Paso 2: Buscar y validar que la Cita existe
        // Reutilizamos el método de CitaService para obtener la cita completa
        Cita cita = buscarCitaPorId(recetaRq.getIdCita());

        // Paso 3: Crear nueva receta y asignar valores
        Receta nuevaReceta = new Receta();
        nuevaReceta.setCita(cita);
        nuevaReceta.setMedicamentoId(recetaRq.getMedicamentoId());
        nuevaReceta.setDosis(recetaRq.getDosis().toLowerCase());
        nuevaReceta.setIndicaciones(recetaRq.getIndicaciones() != null ?
                recetaRq.getIndicaciones().toLowerCase() : null);
        nuevaReceta.setFechaCreacionRegistro(LocalDateTime.now()); // Fecha actual

        // Paso 4: Guardar en base de datos
        recetaRepository.save(nuevaReceta);

        // Paso 5: Retornar respuesta exitosa
        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setMensaje("La receta se ha guardado correctamente.");
        respuesta.setStatus(200);
        return respuesta;
    }

    private void validarCampos(RecetaRq recetaRq) throws BadRequestException {
        if (recetaRq.getIdCita() == null) {
            throw new BadRequestException("El campo IdCita es obligatorio.");
        }
        if (recetaRq.getMedicamentoId() == null) {
            throw new BadRequestException("El campo MedicamentoId es obligatorio.");
        }
        if (recetaRq.getDosis() == null || recetaRq.getDosis().isBlank()) {
            throw new BadRequestException("El campo Dosis es obligatorio.");
        }
    }

    // Método auxiliar para buscar cita por ID
    private Cita buscarCitaPorId(Long citaId) throws BadRequestException {
        return citaRepository.findById(citaId)
                .orElseThrow(() -> new BadRequestException("No existe la cita con id: " + citaId));
    }
}
