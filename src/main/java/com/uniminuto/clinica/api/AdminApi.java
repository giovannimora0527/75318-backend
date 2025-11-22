package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.MedicoRq;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface AdminApi {

    @RequestMapping(value = "/pacientes/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Paciente>> listarPacientes(@RequestHeader("Authorization") String token);


    @RequestMapping(value = "/medicos/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Medico>> listarMedicos(@RequestHeader("Authorization") String token);


    @RequestMapping(value = "/usuarios/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Usuario>> listarUsuarios(@RequestHeader("Authorization") String token);


    @RequestMapping(value = "/paciente/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarPaciente(@RequestHeader("Authorization") String token,
                                                @PathVariable PacienteRq pacienteRq) throws BadRequestException;

    @RequestMapping(value = "/paciente/actualizar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<RespuestaRs> actualizarPaciente(@RequestHeader("Authorization") String token,
                                                   @PathVariable PacienteRq pacienteRq) throws BadRequestException;

    @DeleteMapping("/paciente/eliminar/{idPaciente}")
    ResponseEntity<RespuestaRs> eliminarPaciente(@RequestHeader("Authorization") String token,
                                                 @PathVariable Integer idPaciente) throws BadRequestException;
    ;
    @RequestMapping(value = "/medico/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarMedico(@RequestHeader("Authorization") String token,
                                              @PathVariable MedicoRq medicoRq) throws BadRequestException;

    @RequestMapping(value = "/medico/actualizar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<RespuestaRs> actualizarMedico(@RequestHeader("Authorization") String token,
                                                 @PathVariable MedicoRq medicoRq) throws BadRequestException;

    @DeleteMapping("/medico/eliminar/{idMedico}")
    ResponseEntity<RespuestaRs> eliminarMedico(@RequestHeader("Authorization") String token,
                                               @PathVariable Integer idMedico) throws BadRequestException;
    ;

    @RequestMapping(value = "/usuario/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarUsuario(@RequestHeader("Authorization") String token,
                                               @RequestBody UsuarioRq usuarioNuevo) throws BadRequestException;

    @RequestMapping(value = "/usuario/actualizar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<RespuestaRs> actualizarUsuario(@RequestHeader("Authorization") String token,
                                                  @RequestBody UsuarioRq usuario) throws BadRequestException;


    @DeleteMapping("/usuario/eliminar/{idUsuario}")
    ResponseEntity<RespuestaRs> eliminarUsuario(@RequestHeader("Authorization") String token,
                                                @PathVariable Long idUsuario) throws BadRequestException;

}
