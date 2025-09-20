package com.uniminuto.clinica.service;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface CitaService {

    List<Cita> buscarPorPaciente(Long id)
            throws BadRequestException;

    List<Cita> buscarPorMedico(Long id)
            throws BadRequestException;

    RespuestaRs guardarCita(CitaRq CitaNuevo) throws BadRequestException;

    List<Cita> listarCitasDesc();
}

