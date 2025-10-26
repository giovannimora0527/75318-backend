package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.FormulaMedica;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sebas
 */
@Repository
public interface FormulaMedicaRepository extends JpaRepository<FormulaMedica, Long> {

    List<FormulaMedica> findByPaciente(Paciente paciente);

    List<FormulaMedica> findByMedico(Medico medico);

    List<FormulaMedica> findByFechaEmisionBetween(Date inicio, Date fin);
}
