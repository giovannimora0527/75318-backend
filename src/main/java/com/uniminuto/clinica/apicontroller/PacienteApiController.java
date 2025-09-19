package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author jartunduaga
 */

@RestController
@RequestMapping("/paciente")
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

    @GetMapping("/por-fecha-nacimiento")
    public ResponseEntity<List<Paciente>> listarPacientePorFechaNacimiento(
            @RequestParam (defaultValue = "asc") String orden) {
        List<Paciente> pacientes = pacienteService.listarPacientePorFechaNacimiento(orden);
        return ResponseEntity.ok(pacientes);
    }
}
