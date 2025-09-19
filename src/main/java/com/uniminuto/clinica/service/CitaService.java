package com.uniminuto.clinica.service;
import java.util.List;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.CitaRq;
import org.apache.coyote.BadRequestException;

public interface CitaService {
    RespuestaRs guardarCita(CitaRq citaNueva) throws BadRequestException;
    List<Cita> listarCitasRecientes();

}