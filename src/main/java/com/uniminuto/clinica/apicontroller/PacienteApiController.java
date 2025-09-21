package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD

=======
/**
 *
 * @author JAVIER-CUERVO
 */
>>>>>>> 6ddb292738158152aa065f4d15449b4ce3a7c0c8
@RestController
public class PacienteApiController implements PacienteApi {

    @Autowired
    private PacienteService pacienteService;

    @Override
    public ResponseEntity<List<Paciente>> listarPaciente() {
        return ResponseEntity.ok(pacienteService.listarTodosLosPacientes());
    }
    public ResponseEntity<PacienteService> buscarPorFechaNacimiento(String fecha_nacimiento)
            throws BadRequestException {   
        return null;
    }
    
}

