package com.uniminuto.clinica.repository;

import java.util.List;
import java.util.Optional; 
import java.time.LocalDateTime; 
import com.uniminuto.clinica.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findAllByOrderByFechaHoraDesc();
    
    // CORRECCIÓN: Cambia el tipo de retorno a List para manejar múltiples resultados
    List<Cita> findByMedicoIdAndFechaHora(Long medicoId, LocalDateTime fechaHora);
}