<<<<<<< HEAD
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
>>>>>>> origin/916724_BrayanEscorcha
package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
<<<<<<< HEAD
import com.uniminuto.clinica.model.MedicoRq;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import org.apache.coyote.BadRequestException;
=======
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
>>>>>>> origin/916724_BrayanEscorcha
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
<<<<<<< HEAD
 * @author lmora
 */
@RestController
public class PacienteApiController implements PacienteApi {

    @Autowired
    private PacienteService pacienteService;

    @Override
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listaTodosLosPacientes());
    }

    @Override
    public ResponseEntity<Paciente> buscarPacienteXIdentificacion(String numeroDocumento)
            throws BadRequestException {
        return ResponseEntity.ok(pacienteService.buscarPacientePorDocumento(numeroDocumento));
    }

    @Override
    public ResponseEntity<List<Paciente>> listarPacientesXOrden(String orden) {
        return ResponseEntity.ok(pacienteService.listarOrdenadoPorFechaNacimiento(orden.equals("asc")));
    }


    @Override
    public ResponseEntity<RespuestaRs> guardarPaciente(PacienteRq pacienteNuevo)
            throws BadRequestException {
        return ResponseEntity.ok(this.pacienteService.guardarPaciente(pacienteNuevo));
    }

    @Override
    public ResponseEntity<RespuestaRs> actualizarrPaciente(PacienteRq paciente)
            throws BadRequestException {
        return ResponseEntity.ok(this.pacienteService.actualizarPaciente(paciente));
    }



=======
 * @author Brayan Escorcha
 */
@RestController
public class PacienteApiController implements PacienteApi{
    
    @Autowired
    private PacienteService pacienteService;


    @Override
    public ResponseEntity<List<Paciente>> listarPaciente() {
        return ResponseEntity.ok(this.pacienteService.ListarTodosLosPacientes());

 
    }
>>>>>>> origin/916724_BrayanEscorcha
}
