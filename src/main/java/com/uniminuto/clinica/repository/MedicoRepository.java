/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medico;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author crash
 */
@Repository
public interface MedicoRepository extends JpaRepository<Medico,Long>{
    Optional<Medico> findById(Long id);

    List<Medico> findByEspecializacion(Especializacion e);
}
