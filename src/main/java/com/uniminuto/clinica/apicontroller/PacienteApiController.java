package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.service.PacienteService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author jartunduaga
 */

@RestController
public class PacienteApiController implements PacienteApi {

    @Autowired
    private PacienteService pacienteService;

    @Override
    public ResponseEntity<List<Paciente>> listarPaciente() {
        return ResponseEntity.ok(this.pacienteService.listarLosPacientes());
    }

    @Override
    public ResponseEntity<Paciente>
    encontrarPorDocumento(String numero_documento)
            throws BadRequestException {
        return ResponseEntity.ok(this.pacienteService
                .encontrarPorDocumento(numero_documento));
    }

    @Override
    public ResponseEntity<List<Paciente>> listarPacientePorFechaNacimiento(String orden) {
        return ResponseEntity.ok(this.pacienteService.listarPacientePorFechaNacimiento(orden));
    }

    @Override
    public ResponseEntity<Paciente> guardarPaciente(PacienteRq pacienteRq) throws BadRequestException {
        try {
            Paciente pacienteGuardado = this.pacienteService.guardarPaciente(pacienteRq);
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteGuardado);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al guardar el paciente: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Paciente> actualizarPaciente(Long id, PacienteRq pacienteRq) throws BadRequestException {
        try {
            Paciente pacienteActualizado = this.pacienteService.actualizarPaciente(id, pacienteRq);
            return ResponseEntity.ok(pacienteActualizado);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al actualizar el paciente: " + e.getMessage());
        }
    }
}
