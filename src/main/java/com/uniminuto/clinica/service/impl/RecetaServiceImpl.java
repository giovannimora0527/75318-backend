/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medicamento;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
        validarRecetaRq(recetaRq);

        Optional<Cita> citaOpt = citaRepository.findById(recetaRq.getCitaId());
        if (!citaOpt.isPresent()) {
            throw new BadRequestException("Cita no encontrada con id: " + recetaRq.getCitaId());
        }

        Optional<Medicamento> medicamentoOpt = medicamentoRepository.findById(recetaRq.getMedicamentoId());
        if (!medicamentoOpt.isPresent()) {
            throw new BadRequestException("Medicamento no encontrado con id: " + recetaRq.getMedicamentoId());
        }

        boolean esActualizacion = recetaRq.getId() != null;
        Receta receta = esActualizacion 
            ? recetaRepository.findById(recetaRq.getId()).orElse(new Receta())
            : new Receta();

        // Mapear campos desde el RQ a la entidad
        receta.setCita(citaOpt.get());
        receta.setMedicamento(medicamentoOpt.get());
        receta.setDosis(recetaRq.getDosis());
        receta.setIndicaciones(recetaRq.getIndicaciones());
        
        if (!esActualizacion) {
            receta.setFechaCreacionRegistro(LocalDateTime.now());
        }

        recetaRepository.save(receta);

        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje(esActualizacion ? "Receta actualizada con éxito" : "Receta guardada con éxito");
        return respuesta;
    }

    private void validarRecetaRq(RecetaRq recetaRq) throws BadRequestException {
        if (recetaRq == null) {
            throw new BadRequestException("El cuerpo de la petición no puede ser vacío");
        }
        if (recetaRq.getCitaId() == null) {
            throw new BadRequestException("El campo citaId es obligatorio");
        }
        if (recetaRq.getMedicamentoId() == null) {
            throw new BadRequestException("El campo medicamentoId es obligatorio");
        }
        if (recetaRq.getDosis() == null || recetaRq.getDosis().trim().isEmpty()) {
            throw new BadRequestException("El campo dosis es obligatorio");
        }
    }

    @Override
    public List<RecetaRs> listarRecetas() {
        return recetaRepository.findAllByOrderByFechaCreacionRegistroDesc().stream().map(r -> {
            RecetaRs dto = new RecetaRs();
            dto.setId(r.getId());
            dto.setCitaId(r.getCita().getId());
            dto.setNombrePaciente(r.getCita().getPaciente().getNombres() + " " + r.getCita().getPaciente().getApellidos());
            dto.setNombreMedico(r.getCita().getMedico().getNombres() + " " + r.getCita().getMedico().getApellidos());
            dto.setMedicamentoId(r.getMedicamento().getId());
            dto.setNombreMedicamento(r.getMedicamento().getNombre());
            dto.setDosis(r.getDosis());
            dto.setIndicaciones(r.getIndicaciones());
            dto.setFechaCreacionRegistro(r.getFechaCreacionRegistro());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<RecetaRs> listarRecetasRecientes() {
        return recetaRepository.findAllByOrderByFechaCreacionRegistroDesc().stream()
            .limit(10)
            .map(r -> {
                RecetaRs dto = new RecetaRs();
                dto.setId(r.getId());
                dto.setCitaId(r.getCita().getId());
                dto.setNombrePaciente(r.getCita().getPaciente().getNombres() + " " + r.getCita().getPaciente().getApellidos());
                dto.setNombreMedico(r.getCita().getMedico().getNombres() + " " + r.getCita().getMedico().getApellidos());
                dto.setMedicamentoId(r.getMedicamento().getId());
                dto.setNombreMedicamento(r.getMedicamento().getNombre());
                dto.setDosis(r.getDosis());
                dto.setIndicaciones(r.getIndicaciones());
                dto.setFechaCreacionRegistro(r.getFechaCreacionRegistro());
                return dto;
            }).collect(Collectors.toList());
    }

    @Override
    public List<RecetaRs> listarRecetasPorCita(Long citaId) {
        return recetaRepository.findByCitaId(citaId).stream().map(r -> {
            RecetaRs dto = new RecetaRs();
            dto.setId(r.getId());
            dto.setCitaId(r.getCita().getId());
            dto.setNombrePaciente(r.getCita().getPaciente().getNombres() + " " + r.getCita().getPaciente().getApellidos());
            dto.setNombreMedico(r.getCita().getMedico().getNombres() + " " + r.getCita().getMedico().getApellidos());
            dto.setMedicamentoId(r.getMedicamento().getId());
            dto.setNombreMedicamento(r.getMedicamento().getNombre());
            dto.setDosis(r.getDosis());
            dto.setIndicaciones(r.getIndicaciones());
            dto.setFechaCreacionRegistro(r.getFechaCreacionRegistro());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<RecetaRs> listarRecetasPorMedicamento(Long medicamentoId) {
        return recetaRepository.findByMedicamentoId(medicamentoId).stream().map(r -> {
            RecetaRs dto = new RecetaRs();
            dto.setId(r.getId());
            dto.setCitaId(r.getCita().getId());
            dto.setNombrePaciente(r.getCita().getPaciente().getNombres() + " " + r.getCita().getPaciente().getApellidos());
            dto.setNombreMedico(r.getCita().getMedico().getNombres() + " " + r.getCita().getMedico().getApellidos());
            dto.setMedicamentoId(r.getMedicamento().getId());
            dto.setNombreMedicamento(r.getMedicamento().getNombre());
            dto.setDosis(r.getDosis());
            dto.setIndicaciones(r.getIndicaciones());
            dto.setFechaCreacionRegistro(r.getFechaCreacionRegistro());
            return dto;
        }).collect(Collectors.toList());
    }
}