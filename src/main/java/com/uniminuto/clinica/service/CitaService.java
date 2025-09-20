package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Paciente;
import java.util.List;

public interface CitaService {

    Cita guardarCita(Cita cita);
    List<Cita> obtenerTodas();
    Cita obtenerPorId(Long id);
    List<Cita> listarCitasPorFechaHoraDesc();
}
