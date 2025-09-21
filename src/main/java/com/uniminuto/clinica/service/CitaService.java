package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Cita;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author JAVIER-CUERVO
 */
@Service
public interface CitaService {
    Cita guardarCita(Cita cita);
    List<Cita> listarCitas();
    List<Cita> listarPorPaciente(Long pacienteId);
    List<Cita> listarPorMedico(Long medicoId);
    List<Cita> listarPorFechaHora();
}