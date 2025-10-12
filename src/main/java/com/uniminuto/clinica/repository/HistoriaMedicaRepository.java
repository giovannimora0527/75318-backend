package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.HistoriaMedica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriaMedicaRepository extends JpaRepository<HistoriaMedica, Long> {
    List<HistoriaMedica> findAllByOrderByFechaCreacionDesc();
}
