/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usuario
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    
    /**
     * Lista todas las citas ordenadas por fecha y hora descendente
     */
    List<Cita> findAllByOrderByFechaHoraDesc();
    
    /**
     * Verifica si existe una cita para el mismo médico, paciente y fecha/hora
     */
    boolean existsByMedicoAndPacienteAndFechaHora(Medico medico, Paciente paciente, LocalDateTime fechaHora);
    
    /**
     * Busca citas por paciente
     */
    List<Cita> findByPacienteId(Long pacienteId);
    
    /**
     * Busca citas por médico
     */
    List<Cita> findByMedicoId(Long medicoId);
    
    /**
     * Busca citas por estado
     */
    List<Cita> findByEstado(String estado);
}