package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface CitaService {

    RespuestaRs guardarCita (CitaRq citarq) throws BadRequestException;

    List<Cita> listarCitasRecientes();
}
