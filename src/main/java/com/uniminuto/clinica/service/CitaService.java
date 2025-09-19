package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.CitaRq;
import org.apache.coyote.BadRequestException;

public interface CitaService {
    RespuestaRs guardarCita(CitaRq citaNueva) throws BadRequestException;
}