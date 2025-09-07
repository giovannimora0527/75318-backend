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
 * @author 1079977_JavierCuervo
 */
@RestController
public abstract class PacienteApiController implements PacienteApi {

    @Autowired
    private PacienteService pacienteService;

    @Override
    public ResponseEntity<List<Paciente>> listarPaciente() {
        return ResponseEntity.ok(this.pacienteService.listarTodosLosPacientes());
    }
    @Override
    public ResponseEntity<Paciente> buscarPacientePorNumeroDocumento(String numero_documento)
            throws BadRequestException {
        return ResponseEntity.ok(this.pacienteService.encontrarPorNumeroDocumento(numero_documento));
    }
   
}
