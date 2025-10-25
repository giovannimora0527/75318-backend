<<<<<<< HEAD
package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    Optional<Paciente> findBynombres(String nombresPaciente);

    List<Paciente> findByNombres(String nombresPaciente);


    List<Paciente> findAllByOrderByFechaNacimientoAsc();



    List<Paciente> findAllByOrderByFechaNacimientoDesc();
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Brayan Escorcha
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findByUsuario(String u);
    
     Optional<Paciente> findByUsername(String nombrePaciente);
    
    
>>>>>>> origin/916724_BrayanEscorcha
}
