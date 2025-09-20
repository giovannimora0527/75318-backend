/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Especializacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author crash
 */
@Repository
public interface EspecializacionRepository 
    extends JpaRepository<Especializacion, Long>{
    
    Optional<Especializacion> findByCodigoEspecializacion(String codigo);
    
}
