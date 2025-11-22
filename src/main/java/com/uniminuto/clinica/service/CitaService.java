package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.MedicamentoRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface CitaService {

    /**
     * lista las citas del sistema.
     * @return lista de citas.
     */
    List<Cita> listarCitas();

    RespuestaRs guardarCita(CitaRq citaRq) throws BadRequestException;

    RespuestaRs actualizarCita(CitaRq citaRq) throws BadRequestException;

    RespuestaRs eliminarCita(Integer idCita) throws BadRequestException;

}
