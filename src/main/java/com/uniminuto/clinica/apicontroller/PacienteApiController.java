package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.uniminuto.clinica.security.RoleChecker.checkRole;

/**
 *
 * @author lmora
 */
@RestController
public class PacienteApiController implements PacienteApi {

    @Autowired
    private PacienteService pacienteService;

    @Override
    public ResponseEntity<List<Paciente>> listarPacientes() {
        checkRole();
        return ResponseEntity.ok(pacienteService.encontrarTodosLosPacientes());
    }

    @Override
    public ResponseEntity<Paciente> buscarPacienteXIdentificacion(String numeroDocumento)
            throws BadRequestException {
        checkRole();
        return ResponseEntity.ok(pacienteService.buscarPacientePorDocumento(numeroDocumento));
    }

    @Override
    public ResponseEntity<List<Paciente>> listarPacientesXOrden(String orden) {
        checkRole();
        return ResponseEntity.ok(pacienteService.listarOrdenadoPorNombres(orden.equals("asc")));
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarPaciente(PacienteRq pacienteRq) throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(this.pacienteService.guardarPaciente(pacienteRq));
    }

    @Override
    public ResponseEntity<RespuestaRs> actualizarPaciente(PacienteRq pacienteRq) throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(this.pacienteService.actualizarPaciente(pacienteRq));
    }

    @Override
    public ResponseEntity<RespuestaRs> eliminarPaciente(Integer idPaciente) throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(this.pacienteService.eliminarPaciente(idPaciente));
    }

}
