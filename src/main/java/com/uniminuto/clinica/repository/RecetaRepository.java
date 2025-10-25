/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Receta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usuario
 */
@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {
    List<Receta> findAllByOrderByFechaCreacionRegistroDesc();
    List<Receta> findByCitaId(Long citaId);
    List<Receta> findByMedicamentoId(Long medicamentoId);
}