<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

=======
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.MedicoApi;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.service.MedicoService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
<<<<<<< HEAD
<<<<<<< HEAD
 * @author crash
 */
@RestController
public class MedicoApiController implements MedicoApi{   
=======
 * @author lmora
 */
@RestController
public class MedicoApiController implements MedicoApi {

>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
    @Autowired
    private MedicoService medicoService;

    @Override
    public ResponseEntity<List<Medico>> listarMedicos() {
        return ResponseEntity.ok(this.medicoService.listarMedicos());
    }

    @Override
    public ResponseEntity<List<Medico>>
            listarMedicosporEspecialidad(String codigo)
            throws BadRequestException {
        return ResponseEntity.ok(this.medicoService
                .buscarPorEspecialidad(codigo));
    }

<<<<<<< HEAD
    @Override
    public ResponseEntity<Medico> buscarMedicoId(Long id)
            throws BadRequestException {
        return ResponseEntity.ok(this.medicoService.buscarMedicoId(id));
    }
=======
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
}
