<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

=======
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Especializacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
<<<<<<< HEAD
 * @author crash
 */
@Repository
public interface EspecializacionRepository 
    extends JpaRepository<Especializacion, Long>{
=======
 * @author lmora
 */
@Repository
public interface EspecializacionRepository 
        extends JpaRepository<Especializacion, Long> {
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
    
    Optional<Especializacion> findByCodigoEspecializacion(String codigo);
    
}
