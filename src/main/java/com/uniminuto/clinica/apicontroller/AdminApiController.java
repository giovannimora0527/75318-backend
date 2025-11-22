package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AdminApi;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.MedicoRq;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.service.MedicoService;
import com.uniminuto.clinica.service.PacienteService;
import com.uniminuto.clinica.service.UsuarioService;
import com.uniminuto.clinica.utils.JwtUtil;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.uniminuto.clinica.security.RoleChecker.checkRole;

@RestController
@RequestMapping("/api/admin")
public class AdminApiController implements AdminApi {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private UsuarioService usuarioService;

    private MedicoRq medicoRq;

    private PacienteRq pacienteRq;

    private UsuarioRq usuarioRq;

    // Valida que el token sea de un admin
    private void checkAdmin(String token) {
        if(!"admin".equals(JwtUtil.getRoleFromToken(token))) {
            throw new RuntimeException("No autorizado");
        }
    }

    @Override
    @GetMapping("/pacientes/listar")
    public ResponseEntity<List<Paciente>> listarPacientes(@RequestHeader("Authorization") String token) {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(pacienteService.encontrarTodosLosPacientes());
    }

    @Override
    @GetMapping("/medicos/listar")
    public ResponseEntity<List<Medico>> listarMedicos(@RequestHeader("Authorization") String token) {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(medicoService.listarMedicos());
    }

    @Override
    @GetMapping("/usuarios/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios(@RequestHeader("Authorization") String token) {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(usuarioService.listarTodosLosUsuarios());
    }

    @Override
    @PostMapping("/paciente/guardar")
    public ResponseEntity<RespuestaRs> guardarPaciente(@RequestHeader("Authorization") String token,
                                                       @RequestBody PacienteRq pacienteRq) throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(pacienteService.guardarPaciente(pacienteRq));
    }

    @Override
    @PutMapping("/paciente/actualizar")
    public ResponseEntity<RespuestaRs> actualizarPaciente(@RequestHeader("Authorization") String token,
                                                          @RequestBody PacienteRq pacienteRq) throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(pacienteService.actualizarPaciente(pacienteRq));
    }

    @Override
    @DeleteMapping("/paciente/eliminar/{idPaciente}")
    public ResponseEntity<RespuestaRs> eliminarPaciente(@RequestHeader("Authorization") String token,
                                                        @PathVariable Integer idPaciente) throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(pacienteService.eliminarPaciente(idPaciente));
    }

    @Override
    @PostMapping("/medico/guardar")
    public ResponseEntity<RespuestaRs> guardarMedico(@RequestHeader("Authorization") String token,
                                                     @RequestBody MedicoRq medicoRq) throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(medicoService.guardarMedico(medicoRq));
    }

    @Override
    @PutMapping("/medico/actualizar")
    public ResponseEntity<RespuestaRs> actualizarMedico(@RequestHeader("Authorization") String token,
                                                        @RequestBody MedicoRq medicoRq) throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(medicoService.actualizarMedico(medicoRq));
    }

    @Override
    @DeleteMapping("/medico/eliminar/{idMedico}")
    public ResponseEntity<RespuestaRs> eliminarMedico(@RequestHeader("Authorization") String token,
                                                      @PathVariable Integer idMedico) throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(medicoService.eliminarMedico(idMedico));
    }

    @Override
    @PostMapping("/usuario/guardar")
    public ResponseEntity<RespuestaRs> guardarUsuario(@RequestHeader("Authorization") String token,
                                                      @RequestBody UsuarioRq usuarioNuevo) throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(this.usuarioService.guardarUsuario(usuarioNuevo));
    }

    @Override
    @PutMapping("/usuario/actualizar")
    public ResponseEntity<RespuestaRs> actualizarUsuario(@RequestHeader("Authorization") String token,
                                                         @RequestBody UsuarioRq usuario) throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(usuarioService.actualizarUsuario(usuario));
    }

    @Override
    @DeleteMapping("/usuario/eliminar/{idUsuario}")
    public ResponseEntity<RespuestaRs> eliminarUsuario(@RequestHeader("Authorization") String token,
                                                       @PathVariable Long idUsuario) throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(usuarioService.eliminarUsuario(idUsuario));
    }
}


