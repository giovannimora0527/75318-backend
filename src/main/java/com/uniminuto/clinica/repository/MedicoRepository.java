<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

=======
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medico;
import java.util.List;
<<<<<<< HEAD
import java.util.Optional;

=======
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
<<<<<<< HEAD
 * @author crash
 */
@Repository
public interface MedicoRepository extends JpaRepository<Medico,Long>{
    Optional<Medico> findById(Long id);

=======
 * @author lmora
 */
@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
    List<Medico> findByEspecializacion(Especializacion e);
}
