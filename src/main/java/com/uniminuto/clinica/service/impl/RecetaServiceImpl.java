/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RecetaRs;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.MedicamentoRepository;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.service.RecetaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecetaServiceImpl implements RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Override
    public RespuestaRs guardarReceta(RecetaRq recetaRq) throws BadRequestException {
        if (recetaRq == null) {
            throw new BadRequestException("El cuerpo de la petición no puede ser vacío");
        }

        Receta receta = new Receta();
        receta.setDosis(recetaRq.getDosis());
        receta.setIndicaciones(recetaRq.getIndicaciones());
        // convertir fecha (si viene en String)
        if (recetaRq.getFecha() != null) {
            receta.setFecha(java.time.LocalDate.parse(recetaRq.getFecha()));
        }

        // buscar cita
        citaRepository.findById(recetaRq.getCitaId()).ifPresentOrElse(
                receta::setCita,
                () -> { throw new RuntimeException("Cita no encontrada con id: " + recetaRq.getCitaId()); }
        );

        // buscar medicamento
        medicamentoRepository.findById(recetaRq.getMedicamentoId().intValue()).ifPresentOrElse(
        receta::setMedicamento,
        () -> { throw new RuntimeException("Medicamento no encontrado con id: " + recetaRq.getMedicamentoId()); }
        );

        recetaRepository.save(receta);

        RespuestaRs res = new RespuestaRs();
        res.setMensaje("Receta guardada con éxito");
        res.setStatus(200);
        return res;
    }

    @Override
    public List<RecetaRs> listarRecetas() {
        return recetaRepository.findAll().stream().map(r -> {
            RecetaRs dto = new RecetaRs();
            dto.setId(r.getId());
            dto.setDosis(r.getDosis());
            dto.setIndicaciones(r.getIndicaciones());
            dto.setFecha(r.getFecha() != null ? r.getFecha().toString() : "SIN_FECHA");
            dto.setNombreMedicamento(r.getMedicamento() != null ? r.getMedicamento().getNombre() : "SIN_MEDICAMENTO");
            dto.setNombrePaciente(r.getCita() != null && r.getCita().getPaciente() != null
                    ? r.getCita().getPaciente().getNombres() + " " + r.getCita().getPaciente().getApellidos()
                    : "SIN_PACIENTE");
            dto.setNombreMedico(r.getCita() != null && r.getCita().getMedico() != null
                    ? r.getCita().getMedico().getNombres() + " " + r.getCita().getMedico().getApellidos()
                    : "SIN_MEDICO");
            return dto;
        }).collect(Collectors.toList());
    }
}
