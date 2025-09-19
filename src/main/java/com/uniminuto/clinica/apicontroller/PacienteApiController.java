package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
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
}
