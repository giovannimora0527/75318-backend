<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
=======
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
<<<<<<< HEAD
 * @author crash
 */
@RestController
public class PacienteApiController implements PacienteApi {
    
=======
 * @author lmora
 */
@RestController
public class PacienteApiController implements PacienteApi {

>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
    @Autowired
    private PacienteService pacienteService;

    @Override
<<<<<<< HEAD
    public ResponseEntity<List<Paciente>> listarPacientesDes() {
        return ResponseEntity.ok(this.pacienteService.listarPacientesDes());
    }

    @Override
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(this.pacienteService.listarTodo());
    }
    //Funcion buscar por documento identidad
    @Override
    public ResponseEntity<Paciente> encontrarPorDocumentoIdentidad(String numeroDocumento)
            throws BadRequestException {
        return ResponseEntity.ok(this.pacienteService.encontrarPorDocumentoIdentidad(numeroDocumento));
    }

    @Override
    public ResponseEntity<Paciente> buscarPacienteId(Long id) throws BadRequestException {
        return ResponseEntity.ok(this.pacienteService.buscarPacienteId(id));
    }
=======
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(this.pacienteService.listarPacientes());
    }

    @Override
    public ResponseEntity<Paciente>
            buscarPorDocumento(String documento)
            throws BadRequestException {
        return ResponseEntity.ok(this.pacienteService
                .buscarPorDocumento(documento));
    }

>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
}
