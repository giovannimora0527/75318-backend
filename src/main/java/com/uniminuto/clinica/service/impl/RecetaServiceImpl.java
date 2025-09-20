package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.*;
import com.uniminuto.clinica.service.CitaService;
import com.uniminuto.clinica.service.RecetaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public RespuestaRs crearReceta(RecetaRq recetaRq) throws BadRequestException {
        this.validarFormulario(recetaRq);
        Optional<Receta> optReceta = recetaRepository
                .findById(recetaRq.getId());
        if (optReceta.isPresent()){
            throw new BadRequestException("La receta ya existe");
        }
        Receta nuevo = this.mapearAReceta(recetaRq);
        recetaRepository.save(nuevo);
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Receta creada exitosamente");
        rta.setStatus(200);

        return rta;
    }


    private void validarFormulario(RecetaRq recetaRq) throws BadRequestException {
        if (recetaRq.getDosis() == null || recetaRq.getDosis().isBlank()){
            throw new BadRequestException("La dosis debe ser obligatorio");
        }
        if (recetaRq.getIndicaciones() == null || recetaRq.getIndicaciones().isBlank()){
            throw new BadRequestException("Las indicaciones deben ser obligatorias");
        }
    }
    private Receta mapearAReceta (RecetaRq recetaRq){
        Receta nuevo = new Receta();
        nuevo.setDosis(recetaRq.getDosis());
        nuevo.setIndicaciones(recetaRq.getIndicaciones());
        nuevo.setCita(recetaRq.getCita());
        nuevo.setMedicamento(recetaRq.getMedicamento());
        return nuevo;
    }
}
