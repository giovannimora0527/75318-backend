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
    public RespuestaRs guardarReceta(RecetaRq recetaNueva) throws BadRequestException {
        // 1. Validar que la cita y el medicamento existan
        Optional<Cita> cita = citaRepository.findById(recetaNueva.getCitaId());
        if (!cita.isPresent()) {
            throw new BadRequestException("No existe la cita con el ID proporcionado.");
        }

        Optional<Medicamento> medicamento = medicamentoRepository.findById(recetaNueva.getMedicamentoId());
        if (!medicamento.isPresent()) {
            throw new BadRequestException("No existe el medicamento con el ID proporcionado.");
        }

        // 2. Crear la nueva entidad Receta y setear los campos
        Receta nuevaReceta = new Receta();
        nuevaReceta.setCita(cita.get());
        nuevaReceta.setMedicamento(medicamento.get());
        nuevaReceta.setDosis(recetaNueva.getDosis());
        nuevaReceta.setIndicaciones(recetaNueva.getIndicaciones());

        // 3. Guardar la receta
        this.recetaRepository.save(nuevaReceta);

        // 4. Devolver respuesta OK
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("La receta se ha guardado correctamente.");
        rta.setStatus(200);
        return rta;
    }
}