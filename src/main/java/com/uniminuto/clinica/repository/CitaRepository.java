/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.repository;


import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
/**
 *
 * @author Usuario
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    /**
     * Retorna todas las citas ordenadas de la mas reciente a la mas antigua
     * @return 
     */
    List<Cita> findAllByOrderByFechaHoraDesc();
        boolean existsByMedicoAndPacienteAndFechaHora(Medico medico, Paciente paciente, LocalDateTime fechaHora);

}
